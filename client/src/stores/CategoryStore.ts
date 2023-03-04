import { defineStore } from "pinia";
import { CategoryItem } from "@/types";
import { apiUrl } from "@/services/ApiService";

export const useCategoryStore = defineStore("CategoryStore", {
  state: () => ({
    categoryList: [] as CategoryItem[],
    prevCategory: "Bestseller" as string,
  }),
  actions: {
    updatePrevCategory(category: string) {
      this.prevCategory = category;
    },

    async fetchCategories() {
      const url = apiUrl + "categories/";

      this.categoryList = await fetch(url).then((response) => response.json());
    },
  },
});
