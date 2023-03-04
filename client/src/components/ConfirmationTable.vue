<script setup lang="ts">
import { asDollarsAndCents, bookImageFileName } from "@/utils";

import { useOrderDetailsStore } from "@/stores/OrderDetailsStore";
import { BookItem, OrderDetails } from "@/types";

const orderDetailsStore = useOrderDetailsStore();
const orderDetails: OrderDetails = orderDetailsStore.orderDetails;
</script>

<style scoped>
table {
  border: 1px black solid;
  width: fit-content;
  margin-top: 1em;
  margin-bottom: 1em;
  margin-left: auto;
  margin-right: auto;
  background-color: aliceblue;
}

td {
  display: table-cell;
  padding: 1em 2em;
  background-color: aliceblue;
  vertical-align: middle;
}

tr:nth-child(even) > td {
  background-color: aliceblue;
}

td:nth-child(1) {
  text-align: left;
}

td:nth-child(2) {
  text-align: center;
}

td:nth-child(3) {
  text-align: right;
}
.ordered-book {
  width: 100px;
  height: auto;
}
td.book-image {
  padding: 1em 1em;
}
</style>

<template>
  <table>
    <tr v-for="(item, index) in orderDetails.lineItems" :key="item.productId">
      <td class="book-image">
        <img
          class="ordered-book"
          :src="bookImageFileName(orderDetails.books[index].title)"
          :alt="orderDetails.books[index].title"
        />
      </td>
      <td>
        {{ orderDetails.books[index].title }}
      </td>
      <td>{{ item.quantity }}</td>
      <td>{{ asDollarsAndCents(orderDetails.books[index].price) }}</td>
    </tr>
  </table>
</template>
