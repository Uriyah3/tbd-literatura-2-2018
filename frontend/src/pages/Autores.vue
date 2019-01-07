<template>
  <div class="content">
    <top-navbar/>
    <div class="md-layout">
      <div class="md-layout-item md-medium-size-100 md-xsmall-size-100 md-size-100">
        <chart-most-popular-authors/>
      </div>
    </div>
  </div>
</template>

<script>
import ChartMostPopularAuthors from "@/components/Charts/chart-most-popular-authors";
import TopNavbar from "@/components/TopNavbar/NavAutor";

export default {
  components: {
    ChartMostPopularAuthors,
    TopNavbar
  }
};
</script>
