<template>
  <div class="content">
    <top-navbar/>
    <div class="md-layout">
      <div class="md-layout-item md-medium-size-100 md-xsmall-size-100 md-size-100">
        <chart-genre-details />
      </div>
    </div>
  </div>
</template>

<script>
import ChartGenreDetails from "@/components/Charts/chart-genre-details";
import TopNavbar from "@/components/TopNavbar/NavGenero";

export default {
  components: {
    ChartGenreDetails,
    TopNavbar
  }
};
</script>
