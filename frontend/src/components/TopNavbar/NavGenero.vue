<template>
  <md-toolbar md-elevation="0" class="md-transparent">
    <div class="md-toolbar-row">
      <div class="md-toolbar-section-start">
        <h3 class="md-title">Géneros</h3>
      </div>
      <div class="md-toolbar-section-end">
        <md-button class="md-just-icon md-simple md-toolbar-toggle" :class="{toggled: $sidebar.showSidebar}" @click="toggleSidebar">
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </md-button>

        <div class="md-collapse">
          
          <md-list>
            <md-list-item to="/generos">
              <i class="material-icons" style="font-size:48px;">pie_chart</i>
              <p class="hidden-lg hidden-md">Géneros</p>
              
            </md-list-item>

            <md-list-item to="/generos/rankings">
              <i class="material-icons" style="font-size:48px;">format_list_numbered</i>
              <p class="hidden-lg hidden-md">Dashboard</p>
            </md-list-item>            
          </md-list>
        </div>
      </div>
    </div>

  </md-toolbar>
</template>