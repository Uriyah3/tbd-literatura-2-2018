<template>
  <div class="content">
    <top-navbar/>
    <div class="md-layout">
      <div class="md-layout-item md-medium-size-100 md-xsmall-size-100 md-size-100">
            <ul id="example-1">
                <li  v-for="item in libros" :key="item"
                v-bind:bind="true">
                    {{ item.name }}
                </li>
                </ul>
      </div>
    </div>
  </div>
</template>

<script>
import ChartMostPopularAuthors from "@/components/Charts/chart-most-popular-authors";
import TopNavbar from "@/components/TopNavbar/NavAutor";
import axios from "axios";

export default {
   
    
  components: {
    ChartMostPopularAuthors,
    TopNavbar
  },
   data()
    {
        return {
            libros: []
        };
    },
    mounted() {
    axios({ method: "GET", "url": "http://192.168.0.21:8082/book" }).then(result => {
        
        this.libros = result.data.sort(function(a, b) {
          return a.hits < b.hits;
        });
      }, error => {
        console.error(error);
      });
  }

};
</script>