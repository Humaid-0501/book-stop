// Contains all the custom types we want to use for our application
export interface BookItem {
  bookId: number;
  categoryId: number;
  title: string;
  description: string;
  author: string;
  price: number;
  rating: number;
  isPublic: boolean;
  isFeatured: boolean;
}

export interface CustomerForm {
  name: string;
  address: string;
  phone: string;
  email: string;
  ccNumber: string;
  ccExpiryMonth: number;
  ccExpiryYear: number;
}

export interface Order {
  orderId: number;
  amount: number;
  dateCreated: number;
  confirmationNumber: number;
  customerId: number;
}

export interface LineItem {
  bookId: number;
  orderId: number;
  quantity: number;
}
export interface Customer {
  customerName: string;
  address: string;
  phone: string;
  email: string;
  ccNumber: string;
  ccExpDate: number;
}

export interface OrderDetails {
  order: Order;
  customer: Customer;
  books: BookItem[];
  lineItems: LineItem[];
}

export interface CategoryItem {
  categoryId: number;
  name: string;
}
