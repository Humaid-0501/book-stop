<script setup lang="ts"></script>

<style scoped>
footer.container {
  background: var(--secondary-background-color);
  display: flex;
  flex-direction: column;
  gap: 1em;
  font-family: "Montserrat";
  color: #fff;
  padding: 1em;
}

.footer-bookstore-logo h1 {
  display: flex;
  justify-content: center;
  line-height: 29px;
  font-weight: 500;
}

.footer-subscribe-text {
  display: flex;
  justify-content: center;
  font-weight: 500;
  font-size: 17px;
  line-height: 17px;
  font-variant: small-caps;
}

.footer-subscribe-text h4 {
  font-variant-caps: small-caps;
  font-size: 20px;
  font-weight: 400;
  margin: 0px;
}

.subscribe-input-button {
  display: flex;
  justify-content: center;
  margin-bottom: 10px;
}

.subscribe-input {
  border-radius: 50px;
  padding: 0.5em 0.75em;
  font-size: 14px;
  border-style: solid;
  border-color: #fff;
  background-color: transparent;
}

.subscribe-input-button input::placeholder {
  color: #fff;
  font-weight: 100;
}

.subscribe-button {
  border-radius: 50px;
  padding: 0.5em 0.75em;
  font-size: 14px;
  color: #132540;
  border-style: solid;
  background-color: #fff;
  border-color: #fff;
  margin-left: 10px;
}

.links,
.social-media-icons {
  display: flex;
  justify-content: center;
  font-size: 14px;
  line-height: 12px;
  text-decoration: none;
}

.links a {
  padding: 0 10px;
  color: white;
  text-decoration: none;
  font-size: 12px;
  border-right: 1px solid white;
}

.links .last-link {
  border: none;
}

.social-media-icons {
  text-align: center;
  gap: 1.5em;
}

.social-media-icons i {
  cursor: pointer;
}

.copyright-info p {
  display: flex;
  justify-content: center;
  font-weight: 200;
  font-size: 12px;
}
</style>

<template>
  <footer class="container">
    <section class="footer-bookstore-logo">
      <h1>BookStop</h1>
    </section>
    <section class="links">
      <router-link to="/">About</router-link>
      <router-link to="/">Contact</router-link>
      <router-link to="/">Directions</router-link>
      <router-link to="/">Privacy Policy</router-link>
      <router-link class="last-link" to="/">Help</router-link>
    </section>
    <section class="footer-subscribe-text">
      <h4>get the best deals on your next read</h4>
    </section>
    <section class="subscribe-input-button">
      <form action="#">
        <input
          type="email"
          class="subscribe-input"
          placeholder="Enter your email"
        />
        <button type="submit" class="subscribe-button">Subscribe</button>
      </form>
    </section>
    <section class="social-media-icons">
      <i class="fa-brands fa-facebook fa-xl"></i>
      <i class="fa-brands fa-instagram fa-xl"></i>
      <i class="fa-brands fa-pinterest fa-xl"></i>
      <i class="fa-brands fa-twitter fa-xl"></i>
      <i class="fa-brands fa-linkedin-in fa-xl"></i>
    </section>
    <section class="copyright-info">
      <p>Copyright © 2022. All rights reserved.</p>
    </section>
  </footer>
</template>
