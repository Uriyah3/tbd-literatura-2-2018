<template>
  <div class="content">
    <top-navbar/>
    <div class="md-layout">
      <div class="md-layout-item md-medium-size-100 md-xsmall-size-100 md-size-100">
        <chart-author-details/>
      </div>
    </div>
  </div>
</template>

<script>
import ChartAuthorDetails from "@/components/Charts/chart-author-details";
import TopNavbar from "@/components/TopNavbar/NavAutor";

export default {
  components: {
    ChartAuthorDetails,
    TopNavbar
  }
};
</script>
