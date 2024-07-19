<script setup >
import { useCounterStore } from '@/stores/counter'
import {storeToRefs} from "pinia";


const counter = useCounterStore()

const {count} = storeToRefs(counter)
count.value += 1
console.log(count)
</script>

<template>
index
  <div>
    {{count}}
  </div>
</template>

<style scoped>

</style>