import DashboardLayout from "@/pages/Layout/DashboardLayout.vue";

import Dashboard from "@/pages/Dashboard.vue";
import Autores from "@/pages/Autores.vue";
import Libros from "@/pages/Libros.vue";
import Tiempo from "@/pages/Tiempo.vue";
import Grafo from "@/pages/Grafo.vue";
import Geo from "@/pages/Geo.vue";
import ListAutor from "@/pages/Lists/ListAutor.vue";
import ListLibro from "@/pages/Lists/ListLibro.vue";
import ListGenero from "@/pages/Lists/ListGenero.vue";

import Icons from "@/pages/Icons.vue";
import Maps from "@/pages/Maps.vue";
import TableList from "@/pages/TableList.vue";
import Notifications from "@/pages/Notifications.vue";
import UpgradeToPRO from "@/pages/UpgradeToPRO.vue";


const routes = [
  {
    path: "/",
    component: DashboardLayout,
    redirect: "/generos",
    children: [
      {
        path: "generos",
        name: "Géneros",
        component: Dashboard
      },
      {
        path: "autores",
        name: "Autores",
        component: Autores
      },
      {
        path: "libros",
        name: "Libros",
        component: Libros
      },
      {
        path: "popularidad_temporal",
        name: "Tiempo",
        component: Tiempo
      },
      {
        path: "geo_espacial",
        name: "Geo",
        component: Geo
      },
      {
        path: "relaciones_de_usuarios_y_sus_tweets",
        name: "Grafo",
        component: Grafo
      },
      {
        path: "autores/ranking",
        name: "ListAutor",
        component: ListAutor
      },
      {
        path: "libros/ranking",
        name: "ListLibro",
        component: ListLibro
      },
      {
        path: "generos/ranking",
        name: "ListGenero",
        component: ListGenero
      },
      {
        path: "table",
        name: "Table List",
        component: TableList
      },
      {
        path: "icons",
        name: "Icons",
        component: Icons
      },
      {
        path: "maps",
        name: "Maps",
        meta: {
          hideFooter: true
        },
        component: Maps
      },
      {
        path: "notifications",
        name: "Notifications",
        component: Notifications
      },
      {
        path: "upgrade",
        name: "Upgrade to PRO",
        component: UpgradeToPRO
      }
    ]
  }
];

export default routes;
