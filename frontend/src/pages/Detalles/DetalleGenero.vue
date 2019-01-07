<template>
  <div class="content">
    <top-navbar/>
    <div class="md-layout">
      <div class="md-layout-item md-medium-size-50 md-xsmall-size-50 md-size-50">
        <chart-genre-details />
      </div>
      <div class="md-layout-item md-medium-size-50 md-xsmall-size-50 md-size-50">
        <div class="md-layout-item md-medium-size-100 md-xsmall-size-100 md-size-100">
          <table>
              <thead>
          <tr>
            <th scope="col" style="text-align: left; width: 10rem;">
              <td name="name">Nombre</td>
            </th>
            <th scope="col" style="text-align: left; width: 10rem;">
              <td name="hits">Hits</td>
            </th>
            <th scope="col" style="text-align: left; width: 10rem;">
              <td name="hits">Positivo</td>
            </th>
            <th scope="col" style="text-align: left; width: 10rem;">
              <td name="hits">Neutro</td>
            </th>
            <th scope="col" style="text-align: left; width: 10rem;">
              <td name="hits">Negativo</td>
            </th>
          </tr>
        </thead>
        <tbody slot="body">
          <tr v-for="value in libros" :key="value.id">
            <td ><router-link :to="'/autores/ranking/' + value.id" > {{ value.name }} </router-link> </td>
            <td>{{ value.hits }}</td>
            <td>{{ value.positivo }}</td>
            <td>{{ value.neutro }}</td>
            <td>{{ value.negativo }}</td>

          </tr>
        </tbody>
          </table>            
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import ChartGenreDetails from "@/components/Charts/chart-genre-details";
import TopNavbar from "@/components/TopNavbar/NavGenero";
import axios from "axios";

export default {
  components: {
    ChartGenreDetails,
    TopNavbar
  },
   data()
    {
        return {
            libros: []
        };
    },
    mounted() {
    axios({ method: "GET", "url": "http://192.168.0.21:8082/author/genre/" + this.$route.params.id }).then(result => {
        this.libros = result.data.sort(function(a, b) {
          return a.hits < b.hits;
        });
      }, error => {
        console.error(error);
      });
  }
};
</script>
