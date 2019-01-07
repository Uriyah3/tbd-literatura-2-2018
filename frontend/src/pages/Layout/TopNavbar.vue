<template>
  <md-toolbar md-elevation="0" class="md-transparent" style="position: absolute;top:0;">
    <div class="md-toolbar-row" style="border-bottom: 0; z-index:1000;">
      <div class="md-toolbar-section-end">
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