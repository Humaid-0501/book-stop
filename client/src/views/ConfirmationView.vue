<script setup lang="ts">
import ConfirmationTable from "@/components/ConfirmationTable";
import { asDollarsAndCents } from "@/utils";
import { useCategoryStore } from "@/stores/CategoryStore";
const categoryStore = useCategoryStore();

import { useCartStore } from "@/stores/CartStore";
const cartStore = useCartStore();

import { useOrderDetailsStore } from "@/stores/OrderDetailsStore";
import { computed } from "vue";
const orderDetailsStore = useOrderDetailsStore();
const orderDetails = orderDetailsStore.orderDetails;

const ccExpDate = computed(function (): Date {
  return new Date(orderDetails.customer.ccExpDate);
});

const ccExpYear = computed(function (): number {
  return ccExpDate.value.getFullYear();
});

const ccExpMonth = computed(function (): string {
  let month = ccExpDate.value.getMonth() + 1;
  let result = "" + month;
  if (month < 10) {
    result = "0" + month;
  }
  return result;
});
</script>

<style scoped>
#confirmationView {
  display: grid;
  grid-template-columns: 2fr 3fr;
  margin: 2em;
  justify-items: center;
  align-items: center;
}
ul {
  text-align: left;
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  flex-direction: column;
  margin-left: auto;
  margin-right: auto;
}
ul > li {
  margin: 0.25em;
}
h2,
h3 {
  margin: 0.5em auto;
  text-align: center;
  font-weight: 600;
}
.button {
  margin: 0 auto;
  padding: 1em 1em;
}
.button:hover {
  background-color: #132540;
}

.table-container {
  grid-row-start: 1;
  grid-column-start: 2;
  grid-row-end: 6;
}
.no-order {
  margin: 3em;
}
#no-details {
  display: flex;
  justify-content: center;
  text-align: center;
}
.button {
  padding: 1em 2em;
}
.confirmation-page {
  min-height: calc(100vh - 360px);
}

.button.continue-shopping {
  margin-top: 20px;
}

.personal-info {
  margin-top: 20px;
}

#customerInfo {
  align-items: flex-start;
}
hr {
  border: black solid 1px;
  width: 100%;
  margin: 20px 0;
}
</style>

<template>
  <div class="confirmation-page hero-area">
    <div id="no-details" v-if="!orderDetailsStore.hasOrderDetails()">
      <div>
        <h3 class="no-order">
          We are sorry, the order you requested could not be found
        </h3>
        <router-link class="button" to="/">Go to Home Page</router-link>
      </div>
    </div>
    <div id="confirmationView" v-else>
      <div>
        <h2>Your Order Confirmation</h2>
        <ul>
          <li>
            <strong>Confirmation No:</strong>
            {{ orderDetails.order.confirmationNumber }}
          </li>
          <li>
            Order Placed on:
            {{ new Date(orderDetails.order.dateCreated).toDateString() }} at
            {{ new Date(orderDetails.order.dateCreated).toLocaleTimeString() }}
          </li>
        </ul>
      </div>
      <div></div>
      <div>
        <h3 class="personal-info">Billing Information</h3>
        <ul id="customerInfo">
          <li>
            <strong>Name: </strong>{{ orderDetails.customer.customerName }}
          </li>
          <li><strong>Address: </strong>{{ orderDetails.customer.address }}</li>
          <li><strong>Email: </strong>{{ orderDetails.customer.email }}</li>
          <li><strong>Contact: </strong>{{ orderDetails.customer.phone }}</li>
          <li>
            <strong>Card: </strong>**** **** **** 1111 ({{ ccExpMonth }}-{{
              ccExpYear
            }})
          </li>
          <hr />
          <li>
            <strong>Sub Total Amount: </strong>
            {{
              asDollarsAndCents(
                orderDetails.order.amount - cartStore.cart.surcharge
              )
            }}
          </li>

          <li>
            <strong>Surcharge Amount: </strong
            >{{ asDollarsAndCents(cartStore.cart.surcharge) }}
          </li>
          <li>
            <strong>Total Amount: </strong
            >{{ asDollarsAndCents(orderDetails.order.amount) }}
          </li>
          <hr />
        </ul>
      </div>
      <div class="table-container">
        <confirmation-table id="books"> </confirmation-table>
      </div>
      <router-link
        class="button continue-shopping"
        :to="{
          name: 'category-view',
          params: {
            name: categoryStore.prevCategory,
          },
        }"
        >Continue Shopping</router-link
      >
    </div>
  </div>
</template>
