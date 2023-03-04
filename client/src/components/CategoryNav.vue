<script setup lang="ts">
import { useRoute } from "vue-router";
import { useCategoryStore } from "@/stores/CategoryStore";

const route = useRoute();
const categoryStore = useCategoryStore();
</script>

<style scoped>
.category-page {
  padding-top: 25px;
}

.selected-categories {
  display: inline-flex;
  padding-left: 4rem;
  font-weight: 500;
  font-family: "Montserrat";
}

.category-nav {
  display: inline-flex;
  padding-left: 10px;
  font-family: "Montserrat";
}

.category-buttons {
  display: flex;
  flex-direction: row;
  text-align: center;
  gap: 15px;
}

.button.selected-category-button {
  background-color: #132540;
  color: #fff;
  border-color: #132540;
}

.button.unselected-category-button,
.button.unselected-category-button:visited {
  background-color: transparent;
  color: black;
}

.button.unselected-category-button:active {
  background-color: #132540;
  color: #fff;
  border-color: #132540;
}

.button.unselected-category-button:hover {
  background-color: #2a5391;
  color: #fff;
  border-color: #2a5391;
}

.button {
  background-color: #ffffff;
  color: #ee3f3f;
  border: 1px solid #ee3f3f;
  font-size: 14px;
}

.button.unselected-category-button,
.button.unselected-category-button:visited {
  background-color: transparent;
  color: black;
  border-color: black;
}

.button.unselected-category-button:hover {
  color: white;
  /*border: none;*/
  border-color: #2a5391;
  background-color: #2a5391;
}
</style>

<template>
  <div class="category-page">
    <h2 class="selected-categories">Categories:</h2>
    <nav class="category-nav">
      <ul class="category-buttons">
        <li
          v-for="category in categoryStore.categoryList"
          :key="category.categoryId"
        >
          <router-link
            :to="{ name: 'category-view', params: { name: category.name } }"
            class="button"
            :class="
              category.name === route.params.name
                ? 'selected-category-button'
                : 'unselected-category-button'
            "
          >
            {{ category.name }}
          </router-link>
        </li>
      </ul>
    </nav>
  </div>
</template>
