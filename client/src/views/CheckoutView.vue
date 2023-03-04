<script setup lang="ts">
import { reactive, Ref } from "vue";
import useVuelidate from "@vuelidate/core";
import {
  email,
  helpers,
  maxLength,
  minLength,
  required,
} from "@vuelidate/validators";
import { useCartStore } from "@/stores/CartStore";
const cartStore = useCartStore();
const categoryStore = useCategoryStore();
const cart = cartStore.cart;
import { isCreditCard, isMobilePhone } from "@/utils";
import CheckoutFieldError from "@/components/CheckoutFieldError.vue";
import router from "@/router";
import { useCategoryStore } from "@/stores/CategoryStore";
import { asDollarsAndCents } from "@/utils";

const months: string[] = [
  "January",
  "February",
  "March",
  "April",
  "May",
  "June",
  "July",
  "August",
  "September",
  "October",
  "November",
  "December",
];

const form = reactive({
  name: "",
  address: "",
  phone: "",
  email: "",
  ccNumber: "",
  ccExpiryMonth: new Date().getMonth() + 1,
  ccExpiryYear: new Date().getFullYear(),
  checkoutStatus: "",
});

const rules = {
  name: {
    required: helpers.withMessage("Please provide a name.", required),
    minLength: helpers.withMessage(
      "Name must have at least 4 characters.",
      (value: string) => !helpers.req(value.trim()) || value.trim().length >= 4
    ),
    maxLength: helpers.withMessage(
      "Name can have at most 45 characters.",
      (value: string) => !helpers.req(value.trim()) || value.trim().length <= 45
    ),
  },

  address: {
    required: helpers.withMessage("Please provide an address.", required),
    minLength: helpers.withMessage(
      "Address must have at least 4 characters.",
      (value: string) => !helpers.req(value.trim()) || value.trim().length >= 4
    ),
    maxLength: helpers.withMessage(
      "Address can have at most 45 characters.",
      (value: string) => !helpers.req(value.trim()) || value.trim().length <= 45
    ),
  },

  email: {
    required: helpers.withMessage("Please provide an email.", required),
    email: helpers.withMessage(
      "Please provide a valid email.",
      (value: string) =>
        !helpers.req(value.trim()) || value.trim().search(".*@.+[.].+") !== -1
    ),
  },

  phone: {
    required: helpers.withMessage("Please provide a phone number.", required),
    phone: helpers.withMessage(
      "Please provide a valid phone number.",
      (value: string) => {
        return !helpers.req(value.trim()) || isMobilePhone(value.trim());
      }
    ),
  },

  ccNumber: {
    required: helpers.withMessage("Please provide a credit card.", required),
    ccNumber: helpers.withMessage(
      "Please provide a valid credit card.",
      (value: string) => {
        return !helpers.req(value.trim()) || isCreditCard(value.trim());
      }
    ),
  },
  ccExpiryMonth: {},
  ccExpiryYear: {},
};

const $v = useVuelidate(rules, form);

async function submitOrder() {
  console.log("Submit order");
  const isFormCorrect = await $v.value.$validate();
  if (!isFormCorrect) {
    form.checkoutStatus = "ERROR";
  } else {
    form.checkoutStatus = "PENDING";
    await cartStore
      .placeOrder({
        name: form.name,
        address: form.address,
        phone: form.phone,
        email: form.email,
        ccNumber: form.ccNumber,
        ccExpiryMonth: form.ccExpiryMonth,
        ccExpiryYear: form.ccExpiryYear,
      })
      .then(() => {
        form.checkoutStatus = "OK";
        router.push({ name: "confirmation-view" });
      })
      .catch((reason) => {
        form.checkoutStatus = "SERVER_ERROR";
        console.log("Error placing order", reason);
      });
  }
}

function yearFrom(index: number) {
  return new Date().getFullYear() + index;
}
</script>

<style scoped>
.checkout-page {
  color: var(--secondary-background-color);
}
.checkout-page-body {
  display: flex;
  padding: 1em;
  margin-left: 35%;
  margin-right: 40%;
  justify-content: center;
}

form {
  display: flex;
  flex-direction: column;
  width: 100%;
}

.form-field {
  display: flex;
  justify-content: space-between;
  margin-bottom: 0.3em;
}

form > div {
  margin-bottom: 30px;
}

form > div > div > input,
form > div > select {
  margin-right: 0;
  margin-left: auto;
}

.continue-shopping {
  background: #ffffff;
  border: 2px solid var(--primary-color);
  color: var(--primary-color);
  box-shadow: 0px 4px 4px rgb(0 0 0 / 25%);
  border-radius: 50px;
  padding: 1rem 20px;
  font-style: normal;
  font-weight: 400;
  font-size: 18px;
  width: -moz-fit-content;
  width: fit-content;
  text-decoration: none;
  cursor: pointer;
  align-items: center;
  display: flex;
}

.continue-shopping:hover {
  background: var(--secondary-background-color);
  color: white;
  border-color: transparent;
}

.checkoutStatusBox {
  margin: 1em;
  padding: 1em;
  text-align: center;
}

.complete-purchase {
  background: var(--secondary-background-color);
  border: 2px solid var(--secondary-background-color);
  color: white;
  box-shadow: 0px 4px 4px rgb(0 0 0 / 25%);
  border-radius: 50px;
  padding: 1rem 20px;
  font-style: normal;
  font-weight: 400;
  font-size: 18px;
  width: -moz-fit-content;
  width: fit-content;
  text-decoration: none;
  cursor: pointer;
  align-items: center;
  display: flex;
  margin-left: 90px;
  margin-top: 20px;
  margin-bottom: 20px;
}

.complete-purchase:hover {
  background: white;
  color: var(--secondary-background-color);
  border: 2px solid var(--secondary-background-color);
}

.complete-purchase:disabled {
  background: grey;
  color: white;
  border: none;
  cursor: default;
}

.empty-cart-text {
  display: flex;
  justify-content: center;
  margin: 10px 0;
  font-weight: 400;
}

.empty-cart li {
  display: flex;
  justify-content: center;
  margin: 1em;
}

.month-year {
  display: flex;
}
.month-year select {
  margin-right: 4px;
}

.checkout-heading {
  margin: 1em auto;
  text-align: center;
  font-weight: 500;
}

input,
select {
  background-color: #ffffff;
  color: var(--secondary-background-color);
  border: 1px solid var(--secondary-background-color);
  border-radius: 10px;
  padding: 8px;
  margin-left: 25px;
  margin-right: 75px;
}

label {
  align-self: center;
}

.error-text {
  color: red;
}

.empty-cart {
  min-height: calc(100vh - 450px);
}
</style>

<template>
  <h2 class="checkout-heading">View Checkout Details</h2>
  <div class="checkout-page" v-if="cartStore.cart.numberOfItems != 0">
    <section class="checkout-page-body" v-if="!cart.empty">
      <form @submit.prevent="submitOrder">
        <div>
          <div class="form-field">
            <label for="name">Name</label>
            <input
              type="text"
              size="20"
              id="name"
              name="name"
              v-model.lazy="$v.name.$model"
            />
          </div>
          <div>
            <CheckoutFieldError field-name="name" :validator="$v" />
          </div>
        </div>

        <div>
          <div class="form-field">
            <label for="Address">Address</label>
            <input
              type="text"
              size="20"
              id="address"
              name="address"
              v-model.lazy="$v.address.$model"
            />
          </div>
          <div>
            <CheckoutFieldError field-name="address" :validator="$v" />
          </div>
        </div>

        <div>
          <div class="form-field">
            <label for="phone">Phone</label>
            <input
              class="textField"
              type="text"
              size="20"
              id="phone"
              name="phone"
              v-model.lazy="$v.phone.$model"
            />
          </div>
          <div>
            <CheckoutFieldError field-name="phone" :validator="$v" />
          </div>
        </div>

        <div>
          <div class="form-field">
            <label for="email">Email</label>
            <input
              type="text"
              size="20"
              id="email"
              name="email"
              v-model.lazy="$v.email.$model"
            />
          </div>
          <div>
            <CheckoutFieldError field-name="email" :validator="$v" />
          </div>
        </div>

        <div>
          <div class="form-field">
            <label for="ccNumber">Credit card</label>
            <input
              type="text"
              size="20"
              id="ccNumber"
              name="ccNumber"
              v-model.lazy="$v.ccNumber.$model"
            />
          </div>
          <div>
            <CheckoutFieldError field-name="ccNumber" :validator="$v" />
          </div>
        </div>

        <div class="form-field">
          <label>Expiry</label>
          <div class="month-year">
            <select v-model="$v.ccExpiryMonth.$model">
              <option
                v-for="(month, index) in months"
                :key="index"
                :value="index + 1"
              >
                {{ month }} ({{ index + 1 }})
              </option>
            </select>
            <select v-model="$v.ccExpiryYear.$model">
              <option
                v-for="month in 15"
                :key="month"
                :value="yearFrom(month - 1)"
              >
                {{ yearFrom(month - 1) }}
              </option>
            </select>
          </div>
        </div>

        <input
          type="submit"
          name="submit"
          class="complete-purchase"
          :disabled="form.checkoutStatus === 'PENDING'"
          value="Complete Purchase"
        />

        <section class="checkout-details">
          Your credit card will be charged
          <strong>
            {{
              asDollarsAndCents(
                cartStore.cart.subtotal + cartStore.cart.surcharge
              )
            }}
          </strong>
          <br />
          (<strong>
            {{ asDollarsAndCents(cartStore.cart.subtotal) }}
          </strong>
          +
          <strong>
            {{ asDollarsAndCents(cartStore.cart.surcharge) }}
          </strong>
          shipping)
        </section>

        <section v-show="form.checkoutStatus !== ''" class="checkoutStatusBox">
          <div class="error-text" v-if="form.checkoutStatus === 'ERROR'">
            Error: Please fix the problems above and try again.
          </div>

          <div v-else-if="form.checkoutStatus === 'PENDING'">Processing...</div>

          <div v-else-if="form.checkoutStatus === 'OK'">Order placed...</div>

          <div v-else>An unexpected error occurred, please try again.</div>
        </section>
      </form>
    </section>
  </div>
  <div v-else class="empty-cart">
    <h3 class="empty-cart-text">
      Your cart is empty. Please add items to your cart.
    </h3>
    <ul>
      <li class="cart-buttons">
        <router-link
          :to="'../category/' + categoryStore.prevCategory"
          class="continue-shopping"
          >Continue Shopping</router-link
        >
      </li>
    </ul>
  </div>
</template>
