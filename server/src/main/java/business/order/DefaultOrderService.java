package business.order;

import api.ApiException;
import business.BookstoreDbException;
import business.JdbcUtils;
import business.book.Book;
import business.book.BookDao;
import business.cart.ShoppingCart;
import business.cart.ShoppingCartItem;
import business.customer.Customer;
import business.customer.CustomerDao;
import business.customer.CustomerForm;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.*;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DefaultOrderService implements OrderService {

	private BookDao bookDao;
	private OrderDao orderDao;
	private LineItemDao lineItemDao;
	private CustomerDao customerDao;

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public void setLineItemDao(LineItemDao lineItemDao) {
		this.lineItemDao = lineItemDao;
	}

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}
	@Override
	public OrderDetails getOrderDetails(long orderId) {
		Order order = orderDao.findByOrderId(orderId);
		Customer customer = customerDao.findByCustomerId(order.getCustomerId());
		List<LineItem> lineItems = lineItemDao.findByOrderId(orderId);
		List<Book> books = lineItems
				.stream()
				.map(lineItem -> bookDao.findByBookId(lineItem.getBookId()))
				.collect(Collectors.toList());
		return new OrderDetails(order, customer, lineItems, books);
	}

	@Override
    public long placeOrder(CustomerForm customerForm, ShoppingCart cart) {

		validateCustomer(customerForm);
		validateCart(cart);

		try (Connection connection = JdbcUtils.getConnection()) {
			Date date = getDate(
					customerForm.getCcExpiryMonth(),
					customerForm.getCcExpiryYear());
			return performPlaceOrderTransaction(
					customerForm.getName(),
					customerForm.getAddress(),
					customerForm.getPhone(),
					customerForm.getEmail(),
					customerForm.getCcNumber(),
					date, cart, connection);
		} catch (SQLException e) {
			throw new BookstoreDbException("Error during close connection for customer order", e);
		}

	}
	private Date getDate(String monthString, String yearString) {
		try {
			int month = Integer.parseInt(monthString);
			int year = Integer.parseInt(yearString);
			YearMonth ccExpiryDate = YearMonth.of(year,month);
			LocalDate endMonth = ccExpiryDate.atEndOfMonth();
			ZonedDateTime instant = ZonedDateTime.from(endMonth.atStartOfDay(ZoneId.systemDefault()));
			return Date.from(instant.toInstant());

		} catch (NumberFormatException | NullPointerException | DateTimeException ex) {
			return new Date();
		}
	}

	private int generateConfirmationNumber(){
		Random random = new Random();
		return random.nextInt(999999999);
	}

	private long performPlaceOrderTransaction(
			String name, String address, String phone,
			String email, String ccNumber, Date date,
			ShoppingCart cart, Connection connection) {
		try {
			connection.setAutoCommit(false);
			long customerId = customerDao.create(
					connection, name, address, phone, email,
					ccNumber, date);
			long customerOrderId = orderDao.create(
					connection,
					cart.getComputedSubtotal() + cart.getSurcharge(),
					generateConfirmationNumber(), customerId);
			for (ShoppingCartItem item : cart.getItems()) {
				lineItemDao.create(connection, customerOrderId, item.getBookId(),
						item.getQuantity());
			}
			connection.commit();
			return customerOrderId;
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new BookstoreDbException("Failed to roll back transaction", e1);
			}
			return 0;
		}
	}

	private void validateCustomer(CustomerForm customerForm) {

    	String name = customerForm.getName();
		String address = customerForm.getAddress();
		String phone = customerForm.getPhone();
		String ccNumber = customerForm.getCcNumber();
		String email = customerForm.getEmail();

		if (name == null || name.equals("") || name.length() > 45 || name.length()<4) {
			throw new ApiException.InvalidParameter("Invalid name field");
		}

		if (address == null || address.equals("") || address.length() > 45 || address.length()<4) {
			throw new ApiException.InvalidParameter("Invalid address field");
		}


		if (phone == null){
			throw new ApiException.InvalidParameter("Invalid phone field");
		}

		phone = phone.replaceAll("[^0-9]", "");
		if (phone.equals("") || phone.length()>10 ){
			throw new ApiException.InvalidParameter("Invalid phone field");
		}

		if(ccNumber == null){
			throw new ApiException.InvalidParameter("Invalid credit card number");
		}

		ccNumber = ccNumber.replaceAll("[^0-9]", "");
		if(ccNumber.equals("") || ccNumber.length()>16 || ccNumber.length()<14){
			throw new ApiException.InvalidParameter("Invalid credit card number");
		}

		if(email == null || email.equals("") || isValidEmail(email) ||  email.endsWith(".") || email.length()>30){
			throw new ApiException.InvalidParameter("Invalid email id");
		}

		if(customerForm.getCcExpiryMonth()==null || customerForm.getCcExpiryYear()==null){
			throw new ApiException.InvalidParameter("Invalid expiry date");
		}
		if (expiryDateIsInvalid(customerForm.getCcExpiryMonth(), customerForm.getCcExpiryYear())) {
			throw new ApiException.InvalidParameter("Invalid expiry date");
		}
	}


	private boolean isValidEmail(String email) {
		return !Pattern.compile("^\\S+@\\S+$").matcher(email).matches();
	}

	private boolean expiryDateIsInvalid(String ccExpiryMonth, String ccExpiryYear) {

		if(ccExpiryMonth == null || ccExpiryYear ==null || ccExpiryMonth.equals("") || ccExpiryYear.equals("") )
			return true;
		else if (Integer.parseInt(ccExpiryMonth)<1 || Integer.parseInt(ccExpiryMonth)>12)
			return true;
		if (Integer.parseInt(ccExpiryYear) < YearMonth.now().getYear())
			return true;
		else if (Integer.parseInt(ccExpiryYear) == YearMonth.now().getYear()) {
			if (Integer.parseInt(ccExpiryMonth) < YearMonth.now().getMonthValue()) {
				return true;
			}
		}
		return false;

	}

	private void validateCart(ShoppingCart cart) {

		if (cart.getItems().size() < 1) {
			throw new ApiException.InvalidParameter("Cart is empty.");
		}

		cart.getItems().forEach(item-> {
			if (item.getQuantity() < 1 || item.getQuantity() > 99) {
				throw new ApiException.InvalidParameter("Invalid quantity");
			}
			Book databaseBook = bookDao.findByBookId(item.getBookId());
			if (item.getBookForm().getPrice() != databaseBook.getPrice()) {
				throw new ApiException.InvalidParameter("Invalid price");
			}

			if (item.getBookForm().getCategoryId() != databaseBook.getCategoryId()) {
				throw new ApiException.InvalidParameter("Invalid category");
			}
		});
	}

}
