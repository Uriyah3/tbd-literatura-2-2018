<style src="vue-d3-network/dist/vue-d3-network.css"></style>
<style>

.net {
  height:88vh;
  width:100%;
}

canvas {
  position: relative;
  left: auto;
  top: auto;
}

</style>

<template>
  <div>
    <top-navbar/>
    <div class="content" style="min-height: 88vh;margin-top:1vh;">
      <d3-network ref='net' :net-nodes="nodes" :net-links="links" :options="options" />
  </div>
  </div>
</template>


<script>
import D3Network from 'vue-d3-network';
import TopNavbar from "@/pages/Layout/TopNavbar";

export default {
  components: {
    D3Network,
    TopNavbar
  },
  data () {
    return {
      nodes: [
        { id: 1, name: 'my awesome node 1'},
        { id: 2, name: 'my node 2'},
        { id: 3, name:'orange node', _color: 'yellow', _size: 40 },
        { id: 4, _color: '#0022ff'},
        { id: 5 },
        { id: 6 },
        { id: 7 },
        { id: 8 },
        { id: 9 }
      ],
      links: [
        { sid: 1, tid: 2, _color:"green" },
        { sid: 2, tid: 8 },
        { sid: 3, tid: 4 },
        { sid: 4, tid: 5 },
        { sid: 5, tid: 6 },
        { sid: 7, tid: 8 },
        { sid: 5, tid: 8 },
        { sid: 3, tid: 8 },
        { sid: 7, tid: 9 }
      ],
      nodeSize:20,
      canvas:false

    }
  },
  computed:{
    options(){
      return{
        force: 3000,
        nodeSize: this.nodeSize,
        nodeLabels: true,
        canvas: this.canvas
      }
    }
  }
};
</script>