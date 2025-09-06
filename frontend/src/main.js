import { createApp } from "vue";
import App from "./App.vue";
import router from "./router"; // Import Vue Router
import store from "./store/index.js"; // Import Vuex Store

const app = createApp(App);

app.use(router); // Use Vue Router
app.use(store);  // Use Vuex Store

app.mount("#app"); // Mount the app
