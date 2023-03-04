<script setup lang="ts">
import { defineProps, Ref } from "vue";
import { Validation } from "@vuelidate/core";

const props = defineProps<{
  fieldName: string;
  validator: Ref<Validation>;
}>();
</script>

<style scoped>
form > div > div > .error {
  color: red;
  text-align: right;
  margin-left: auto;
  margin-right: 0;
  font-size: 15px;
}
</style>

<template>
  <template v-if="validator[fieldName].$error">
    <div
      class="error"
      v-for="error of validator[fieldName].$errors"
      :key="error.$uid"
    >
      {{ error.$message }}
    </div>
  </template>
</template>
