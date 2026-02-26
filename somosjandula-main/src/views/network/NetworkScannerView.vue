<template>

  <div class="contenedor">
    <Tarjeta v-for="item in listaDeDatos" :key="item.nombreRed" :nombreRed="item.nombreRed" :estado="item.estado"
      :fecha="item.fecha" :hora="item.hora" />
  </div>

  <button class="administrar" type="button" @click="mostrarMenu">Administrar</button>

  <div class="administracion" v-show="mostrarAdministracion">

    <div>
      <h4>Lista de redes disponibles</h4>
      <ul>
        <li v-for="item in listaRedes" :key="item.ssid">
          <span>{{ item.ssid }} </span><button class="borrarred" :id="item.ssid"
            @click="borrarRed(item.ssid)">Borrar</button>
        </li>
      </ul>
    </div>

    <div>
      <form @submit.prevent="enviarNuevaRed">

        <h4>Agregar nueva red</h4>

        <label for="ssid">Nombre de la Red:</label>
        <input type="text" id="ssid" name="ssid" placeholder="Ejemplo: Andared_Corporativo"
          v-model.trim="nuevaRed.ssid" required>

        <label for="password">Contraseña</label>
        <input type="password" id="password" name="password" placeholder="******" v-model="nuevaRed.password" required>

        <label for="configuracion">Configuración de red:</label>
        <input id="configuracion" name="configuracion" v-model.trim="nuevaRed.seguridad" required>

        <button id="newred" type="submit">Agregar Red</button>

      </form>
    </div>

    <div>
      <h4>Establer tiempo de consulta de redes</h4>

      <form id="establecerTiempo" @submit.prevent="enviarTiempoConsulta">
        <label for="tiempoConsulta">Tiempo de consulta:</label>
        <div>
          <input type="number" id="tiempoConsulta" name="tiempoConsulta" min="1" max="3600"
            v-model.number="tiempoConsulta" required> <span>segundos</span>
        </div>
        <button id="establecer" type="submit">Establecer Tiempo</button>
      </form>

    </div>

  </div>


</template>

<script setup>
import { ref, onMounted } from 'vue'
import Tarjeta from '../../components/redes/Tarjeta.vue'
import { obtenerTokenJWTValido } from '@/services/firebaseService'
import { crearToast } from '@/utils/toast.js'

const temporizador = 11000;
const apiUrl = 'http://localhost:8084';
const listaDeDatos = ref([]);
const listaRedes = ref([]);
const mostrarAdministracion = ref(false);
const toastMessage = ref('');
const toastColor = ref('success');
const isToastOpen = ref(false);
const nuevaRed = ref({
  ssid: '',
  password: '',
  seguridad: ''
});
const tiempoConsulta = ref(null);

function mostrarMenu() {
  mostrarAdministracion.value = !mostrarAdministracion.value;
    listarRedes();
}

//--------------Llamada a la API para pedir las redes(Ahora mismo no hay server asi que lo comento)----------------------
async function datosRedes() {
  try {
    const response = await fetch(`${apiUrl}/registros-redes`);
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
  listarRedes();
  datosRedes();
  setInterval(datosRedes, temporizador)
});


//-----------------Llamada a la API para ver la lista de redes----------------------
async function listarRedes() {
  try {
    const response = await fetch(`${apiUrl}/configuracion-redes`);
    if (!response.ok) {
      throw new Error(`Error ${response.status}: ${response.statusText}`);
    }

    const myData = await response.json();

    listaRedes.value = myData;

  } catch (error) {
    console.error("Hubo un error al obtener los datos:", error);
  }
}

//-----------------Llamada a la API para borrar una red----------------------
async function borrarRed(ssid) {
  try {
    const token = await obtenerTokenJWTValido(toastMessage, toastColor, isToastOpen);

    const response = await fetch(`${apiUrl}/configuracion-redes/${encodeURIComponent(ssid)}`, {
      method: 'DELETE',
      headers: {
        Authorization: `Bearer ${token}`
      }
    });

    if (!response.ok) {
      throw new Error(`Error ${response.status}: ${response.statusText}`);
    }

    await listarRedes();
    crearToast(toastMessage, toastColor, isToastOpen, 'success', 'Red eliminada correctamente');
  } catch (error) {
    console.error('Hubo un error al borrar la red:', error);
    crearToast(toastMessage, toastColor, isToastOpen, 'danger', error.message || 'Error al borrar la red');
  }
}

//-----------------Guardar un nueva red----------------------
async function enviarNuevaRed() {
  try {
    const token = await obtenerTokenJWTValido(toastMessage, toastColor, isToastOpen);

    const response = await fetch(`${apiUrl}/configuracion-redes`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`
      },
      body: JSON.stringify(nuevaRed.value)
    });

    if (!response.ok) {
      throw new Error(`Error ${response.status}: ${response.statusText}`);
    }

    nuevaRed.value = { ssid: '', password: '', seguridad: '' };
    
    await listarRedes();
    await datosRedes();
    crearToast(toastMessage, toastColor, isToastOpen, 'success', 'Red agregada correctamente');
  } catch (error) {
    console.error('Hubo un error al agregar la red:', error);
    crearToast(toastMessage, toastColor, isToastOpen, 'danger', error.message || 'Error al agregar la red');
  }
}





</script>

<style scoped>
* {
  padding: 0;
  margin: 0;
}

.contenedor {
  margin: 0;
  padding: 50px;
  width: 100%;
  height: 100%;
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 30px;
  transition: background-color 0.3s ease;
}

li {
  background-color: rgba(15, 15, 15, 0.808);
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  border-radius: 10px;
}

ul {
  display: flex;
  flex-direction: column;
  gap: 10px;
  list-style-type: none;
  padding: 0;

}

#newred {
  background-color: #28a745;
  color: white;
  font-size: 1.2em;
  border: none;
  border-radius: 5px;
  padding: 10px 10px;
  cursor: pointer;
  margin-top: 1.2em;
}

#newred:hover {
  background-color: #1e7e34;
}

.borrarred {
  background-color: #FF4136;
  color: white;
  border: none;
  border-radius: 5px;
  padding: 5px 10px;
  cursor: pointer;
  margin-left: 20px;
}

h4 {
  margin-bottom: 30px;
  color: #61adff;
  font-weight: bold;
}

input {
  padding: 5px;
  border-radius: 5px;
  border: 1px solid #ccc;
}



.borrarred:hover {
  background-color: #a00a0a;
}

#tiempoConsulta {
  padding: 5px;
  border-radius: 5px;
  border: 1px solid #ccc;
  width: 100px;
}

.administrar {
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

form {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.administracion {
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

.administracion div {
  background-color: #1f1f1f;
  padding: 20px;
  border-radius: 10px;
  width: 400px
}

@media screen and (max-width: 768px) {

  .contenedor {
    display: flex;
    flex-direction: column;
    gap: 20px;
    padding: 0;
  }


  .administracion {
    flex-direction: column;
    width: 90%;
    right: 5%;
    bottom: 80px;
  }

}
</style>
