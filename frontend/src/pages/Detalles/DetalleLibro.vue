<template>
  <div class="content">
    <top-navbar/>
    <div class="md-layout">
      <div class="md-layout-item md-medium-size-100 md-xsmall-size-100 md-size-100">
        <chart-book-details/>
      </div>
    </div>
  </div>
</template>

<script>
import ChartBookDetails from "@/components/Charts/chart-book-details";
import TopNavbar from "@/components/TopNavbar/NavAutor";

export default {
  components: {
    ChartBookDetails,
    TopNavbar
  }
};
</script>
