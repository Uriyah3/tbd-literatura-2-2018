<template>
  <md-toolbar md-elevation="0" class="md-transparent">
    <div class="md-toolbar-row">
      <div class="md-toolbar-section-start">
        <h3 class="md-title">Libros</h3>
      </div>
      <div class="md-toolbar-section-end">
        <div>
          <md-list>
            <md-list-item to="/libros">
              <i class="material-icons" style="font-size:48px;">pie_chart</i>
              
            </md-list-item>

            <md-list-item to="/libros/ranking">
              <i class="material-icons" style="font-size:48px;">format_list_numbered</i>
            </md-list-item>            
          </md-list>
        </div>

        <md-button class="md-just-icon md-simple md-toolbar-toggle" :class="{toggled: $sidebar.showSidebar}" @click="toggleSidebar"  style="width:48px;">
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </md-button>
      </div>
    </div>

  </md-toolbar>
</template>

<script>
export default {
  methods: {
    toggleSidebar() {
      this.$sidebar.displaySidebar(!this.$sidebar.showSidebar);
    }
  }
};
</script>