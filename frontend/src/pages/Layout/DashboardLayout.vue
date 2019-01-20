<template>
  <div class="wrapper" :class="{'nav-open': $sidebar.showSidebar}">
    <notifications></notifications>

    <side-bar>
      <mobile-menu slot="content"></mobile-menu>
      <sidebar-link to="/generos">
        <md-icon>dashboard</md-icon>
        <p>Géneros</p>
      </sidebar-link>
      <sidebar-link to="/autores">
        <md-icon>person</md-icon>
        <p>Autores</p>
      </sidebar-link>
      
      <sidebar-link to="/libros">
        <md-icon>library_books</md-icon>
        <p>Libros</p>
      </sidebar-link>

      <sidebar-link to="/popularidad_temporal">
        <md-icon>timeline</md-icon>
        <p>Popularidad temporal</p>
      </sidebar-link>

      <sidebar-link to="/relaciones_de_usuarios_y_sus_tweets">
        <md-icon>group_work</md-icon>
        <p>Grafo usuarios/géneros</p>
      </sidebar-link>
      
      <sidebar-link to="/map_libro">
        <md-icon>terrain</md-icon>
        <p>Mapas</p>
      </sidebar-link>
    </side-bar>

    <div class="main-panel">
      <dashboard-content>

      </dashboard-content>

      <content-footer v-if="!$route.meta.hideFooter"></content-footer>
    </div>
  </div>
</template> 
<style lang="scss">
</style>

<script>
import ContentFooter from "./ContentFooter.vue";
import DashboardContent from "./Content.vue";
import MobileMenu from "@/pages/Layout/MobileMenu.vue";

export default {
  components: {
    DashboardContent,
    ContentFooter,
    MobileMenu
  }
};
</script>
