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



  <button class="administrar" type="button" @click="mostrarMenu">Administrar</button>


  <div class="administracion" v-show="mostrarAdministracion">

    <div>
    <h4>Lista de redes disponibles</h4>

    <ul>
      <li v-for="item in listaDeDatos" :key="item.nombreRed">
        <span>{{ item.nombreRed }} </span><button class="borrarred" :id="item.nombreRed" @click="borrarRed(item.nombreRed)">Borrar</button>
      </li>
    </ul>


    </div>



<div>
    <form >

      <h4>Agregar nueva red</h4>

      <label for="nombreRed">Nombre de la Red:</label>
      <input type="text" id="nombreRed" name="nombreRed" placeholder="Ejemplo: Andared_Corporativo">

      <label for="password">Contraseña</label>
      <input type="password" id="password" name="password" placeholder="******">

      <label for="fecha_hora">Configuración de red:</label>
      <input id="configuracionred" name="configuracionred">

      <button id="newred" type="submit">Agregar Red</button>

    </form>
    </div>

    <div>
      <h4>Establer tiempo de consulta de redes</h4>

      <form id="establecerTiempo">
        <label for="tiempoConsulta">Tiempo de consulta:</label>
        <div>
        <input type="number" id="tiempoConsulta" name="tiempoConsulta" min="1" max="3600"> <span>segundos</span>
        </div>
        <button id="establecer" type="submit">Establecer Tiempo</button>
      </form>

    </div>

  </div>
  

</template>

<script setup>
import { ref, onMounted, h } from 'vue' 
import Tarjeta from '../../components/redes/Tarjeta.vue'

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

const mostrarAdministracion = ref(false);

function mostrarMenu() {
  mostrarAdministracion.value = !mostrarAdministracion.value;
}


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
}

onMounted(() => {
  pideDatos();
  setInterval(pideDatos, 20000)
});

 */

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

li{
  background-color: rgba(15, 15, 15, 0.808);
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  border-radius: 10px;
}

ul{
  display: flex;
  flex-direction: column;
  gap: 10px;
  list-style-type: none;
  padding: 0;

}

#newred{
  background-color: #28a745;
  color: white;
  font-size: 1.2em;
  border: none;
  border-radius: 5px;
  padding: 10px 10px;
  cursor: pointer;
  margin-top: 1.2em;
}

#newred:hover{
  background-color: #1e7e34;
}

.borrarred{
  background-color: #FF4136;
  color: white;
  border: none;
  border-radius: 5px;
  padding: 5px 10px;
  cursor: pointer;
  margin-left: 20px;
}

h4{
  margin-bottom: 30px;
  color:#61adff;
  font-weight: bold;
}

input{
  padding: 5px;
  border-radius: 5px;
  border: 1px solid #ccc;
}



.borrarred:hover{
  background-color: #a00a0a;
}

#tiempoConsulta{
  padding: 5px;
  border-radius: 5px;
  border: 1px solid #ccc;
  width: 100px;
}

.administrar{
  position: fixed;
  bottom: 20px;
  right: 20px;
  padding: 10px 20px;
  background-color: #007BFF;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

form{
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.administracion{
  border-radius: 30px;
  display: flex;
  flex-direction: row;
  gap: 30px;
  position: fixed;
  bottom: 80px;
  right: 20px;
  background-color: #00000042;
  padding: 20px;
}

.administracion div{
  background-color: #1f1f1f;
  padding: 20px;
  border-radius: 10px;
  width: 400px
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

