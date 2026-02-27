<template>
    <div class="card">
        <div class="contenedorred"> 
            <div class="divestado" :class="{ 'divrojo': props.estado === 'SIN_SENAL', 'divverde': props.estado === 'CONECTADO', 'divamarillo': props.estado === 'FALLO_AUTH' }"></div>
            <h3>{{ nombreRed }}</h3>
        </div> 
    </div>
</template>

<script setup>
import { computed } from 'vue';

const props = defineProps({
    nombreRed: String,
    estado: String
});


</script>

<style scoped>

p, h3{
    margin: 0;
}

h3{
    color: rgb(26, 25, 25);
    font-size: 0.9em;
    font-weight: bold;
}

.fecha{
    color: gray;
    font-size: 0.8em;
    margin-right: 5px;
}

.card{
    backdrop-filter: blur(4px);
    background-color: rgba(255, 255, 255, 0.575);
    border-radius: 20px;
    display: flex;
    flex-direction: row;
    padding: 7px 10px;
    align-items: center;
    gap: 10px;
    box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.2);
    justify-content: space-between;
    
}



.divestado{
    width: 17px;
    height: 17px;
    border-radius: 50%;
    background-color: gray;
    margin-right: 0.6em;
    align-items: center;
    align-content: center;
}

.divrojo{
    background-color: #c20202;
}

.divverde{
    background-color: rgb(39, 231, 125);
}

.contenedorred{
    display: flex;
}

.divamarillo{
    background-color: #f0ec00;
}

.bordeamarillo{
    border-color: #f0ec00;
    background-color: #ecf00073;
}

.textverde{
color:rgb(33, 219, 132);
}

.textrojo{
color: #f01616;
}

.textamarillo{
color: #f0ad00;
}

</style>