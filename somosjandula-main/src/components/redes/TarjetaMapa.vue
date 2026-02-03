<template>
    <div class="card" >
            <div class="contenedorred"> 
    <div class="divestado" :class="{ 'divrojo': props.estado === 'SIN_SENAL', 'divverde': props.estado === 'CONECTADO', 'divamarillo': props.estado === 'FALLO_AUTH' }"></div>
  
        <h3>{{ nombreRed }}</h3>
        <p class="estado" :class="{ 'textrojo': props.estado === 'SIN_SENAL', 'textverde': props.estado === 'CONECTADO', 'textamarillo': props.estado === 'FALLO_AUTH' }">{{ estado }}</p>
    </div> 
        <p class="fecha">{{ fecha_hora }}</p>
    </div>
</template>

<script setup>

const props = defineProps({
    nombreRed: String,
    estado: String,
    fecha_hora: String
});

</script>

<style scoped>

p, h3{
    margin: 0;
}

h3{
    color: rgb(26, 25, 25);
    font-size: 1.2em;
}

.card{
    background-color: rgba(255, 255, 255, 0.842);
    border-radius: 20px;
    display: flex;
    flex-direction: row;
    padding: 10px;
    align-items: center;
    gap: 10px;
    width: 500px;
    box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.2);
    justify-content: space-between;
}

.divestado{
    width: 20px;
    height: 20px;
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