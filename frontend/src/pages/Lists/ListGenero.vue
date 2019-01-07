<template>
  <div class="content">
    <top-navbar/>
    <div class="md-layout">
      <div class="md-layout-item md-medium-size-100 md-xsmall-size-100 md-size-100">
        <SortedTable :values="values">
            <thead>
        <tr>
          <th scope="col" style="text-align: left; width: 10rem;">
            <SortLink name="name">Nombre</SortLink>
          </th>
          <th scope="col" style="text-align: left; width: 10rem;">
            <SortLink name="hits">Hits</SortLink>
          </th>
          <th scope="col" style="text-align: left; width: 10rem;">
            <SortLink name="hits">Positivo</SortLink>
          </th>
          <th scope="col" style="text-align: left; width: 10rem;">
            <SortLink name="hits">Neutro</SortLink>
          </th>
          <th scope="col" style="text-align: left; width: 10rem;">
            <SortLink name="hits">Negativo</SortLink>
          </th>
        </tr>
      </thead>
      <tbody slot="body">
        <tr v-for="value in generos" :key="value.id">
          <td ><router-link :to="'/generos/ranking/' + value.id" > {{ value.name }} </router-link> </td>
          <td>{{ value.hits }}</td>
          <td>{{ value.positivo }}</td>
          <td>{{ value.neutro }}</td>
          <td>{{ value.negativo }}</td>
          

        </tr>
      </tbody>
        </SortedTable>            
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
            generos: []
        };
    },
    mounted() {
    axios({ method: "GET", "url": "http://192.168.0.21:8082/genre" }).then(result => {
        
        this.generos = result.data.sort(function(a, b) {
          return a.hits < b.hits;
        });
      }, error => {
        console.error(error);
      });
  }

};
</script>