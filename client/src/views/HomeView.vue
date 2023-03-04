<script setup lang="ts"></script>

<style scoped>
.home-page {
  display: flex;
  justify-content: space-between;
}

.hero-image {
  padding: 10px 4rem 10px 0;
}

.background-images {
  position: absolute;
  z-index: -1;
  top: 180px;
  left: 200px;
}

.background-images img {
  border-radius: 50%;
  object-fit: cover;
}

.bg-img-3 {
  position: absolute;
  width: 280px;
  height: 280px;
  opacity: 0.1;
  top: 10px;
  left: 280px;
}

.bg-img-2 {
  position: absolute;
  width: 250px;
  height: 250px;
  opacity: 0.1;
  top: 240px;
  left: 130px;
}

.bg-img-1 {
  width: 280px;
  height: 280px;
  transform: rotate(25deg);
  opacity: 0.2;
}

.welcome-text {
  width: 20em;
  color: black;
  background-color: transparent;
  font-family: var(--welcome-text-content);
  box-sizing: unset;
  padding-left: 4rem;
}

.welcome-text h2 {
  font-family: var(--welcome-text-header);
  font-weight: 800;
}

.null-link {
  text-decoration: none;
}

.null-link:hover {
  color: #132540;
}

.cta-button {
  padding: 10px 15px;
  border-radius: 10px;
  color: white;
  border: none;
  background-color: #c02929; /*#D54242*/
  font-weight: 700;
  font-size: 20px;
}

.cta-button:hover {
  background-color: #ee3f3f;
}

@media (max-width: 1200px) {
  .bg-img-1,
  .bg-img-2,
  .bg-img-3 {
    width: 240px;
    height: 240px;
  }

  .bg-img-3 {
    left: 250px;
  }
  .background-images {
    left: 120px;
  }
  .bg-img-2 {
    left: 100px;
    top: 200px;
  }
}

@media (min-width: 1400px) {
  .background-images {
    left: 200px;
  }
}
</style>
<template>
  <div class="home-page">
    <div class="background-images">
      <img
        class="bg-img-3"
        src="@/assets/images/site/circle-3.jpg"
        alt="backgroung-3"
      />
      <img
        class="bg-img-2"
        src="@/assets/images/site/cirlce-2.jpg"
        alt="backgroung-2"
      />
      <img
        class="bg-img-1"
        src="@/assets/images/site/circle-1.jpg"
        alt="backgroung-1"
        width="374px"
        height="374px"
      />
    </div>
    <section class="welcome-text flow-content container dark-background">
      <h2>
        Amazing eBooks for <br />
        great prices
      </h2>

      <p>The only stop for all the book lovers.</p>
      <p>Get the bestselling titles from various genres.</p>
      <p>
        Read it anywhere, anytime, and on your favorite device when purchasing
        eBooks from
        <router-link class="null-link" to="/">BookStop:</router-link>
      </p>
      <button
        class="button cta-button"
        @click="$router.push('/category/Bestseller')"
      >
        Shop Now!
      </button>
    </section>
    <section class="category-images">
      <div class="hero-image">
        <img
          src="@/assets/images/site/hero.jpg"
          alt="Hero image"
          width="370"
          height="487"
        />
      </div>
    </section>
  </div>
</template>
