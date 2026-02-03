<template>

  <div class="contenedor">
    <Tarjeta
      v-for="item in listaDeDatos"
      :key="item.nombreRed"
      :nombreRed="item.nombreRed"
      :estado="item.estado"
      :fecha_hora="item.fecha_hora"
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue' 
import Tarjeta from '../../components/redes/Tarjeta.vue'
import TarjetaMapa from '@/components/redes/TarjetaMapa.vue';

//Inicializamos como un ref con redes de prueba
//-------------------------------------------------------------------
const listaDeDatos = ref([
  {
    nombreRed: 'Andared_Corporativo',
    estado: 'CONECTADO',
    fecha_hora: '2026-02-03 09:15'
  },
  {
    nombreRed: 'Buscando...',
    estado: 'FALLO_AUTH',
    fecha_hora: '2026-02-03 08:40'
  },
  {
    nombreRed: 'Cargando...+',
    estado: 'SIN_SENAL',
    fecha_hora: '2026-02-03 07:55'
  },
    {
    nombreRed: 'Andared_IoT',
    estado: 'SIN_SENAL',
    fecha_hora: '2026-02-03 07:55'
  },
  {
    nombreRed: 'Andared',
    estado: 'CONECTADO',
    fecha_hora: '2026-02-03 07:55'
  }
  
]);
//-------------------------------------------------------------------


//--------------Llamada a la API para pedir las redes(Ahora mismo no hay server asi que lo comento)----------------------
/* async function pideDatos() {
  try {
    const response = await fetch('http://localhost:8080/registros-redes');
    
    if (!response.ok) {
      throw new Error(`Error ${response.status}: ${response.statusText}`);
    }

    const myData = await response.json();
    
    listaDeDatos.value = myData;

  } catch (error) {
    console.error("Hubo un error al obtener los datos:", error);
  }
} */

/* onMounted(() => {
  pideDatos();
  setInterval(pideDatos, 20000)
}); */




</script>

<style scoped>

*{
  padding: 0;
  margin:0;
}

.contenedor{
  margin: 0;
  padding: 50px;
  width: 100%;
  height: 100%;
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 30px;
  transition: background-color 0.3s ease;
}


button{
  border-radius: 50%;
  height: 40px;
  width: 40px;
  position: absolute;
  bottom: 30px;
  right: 30px;
  background-color: white;
  border: none;
  box-shadow: 0 2px 8px rgba(0,0,0,0.2);
  cursor: pointer;
}

@media screen and (max-width: 768px){
  
.contenedor{
  display: flex;
  flex-direction: column;
  gap: 20px;
  padding: 0;
}


}
</style>

