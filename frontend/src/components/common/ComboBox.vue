<script setup>
import { CheckIcon } from 'lucide-vue-next'
import { Button } from '../ui/button'
import {
  Command,
  CommandEmpty,
  CommandGroup,
  CommandInput,
  CommandItem,
  CommandList,
} from '../ui/command'
import { Popover, PopoverContent, PopoverTrigger } from '../ui/popover'
import { ref, computed, onMounted } from 'vue'
import { cn } from '@/lib/utils'

onMounted(() => {
  // 여기서 attractionList 데이터 받아오기?
  console.log('ComboBox mounted')
})

const value = ref('')

const attractions = ref([
  {
    id: 1,
    title: '경복궁',
  },
  {
    id: 2,
    title: '남산서울타워',
  },
  {
    id: 3,
    title: '명동 쇼핑거리',
  },
  {
    id: 4,
    title: '홍대 걷고싶은거리',
  },
  {
    id: 5,
    title: '광장시장',
  },
])

const pickedAttraction = defineModel('modelValue')

const selectedValue = ref('')
const searchQuery = ref('')
const open = ref(false)

// 검색 필터링
const filteredAttractions = computed(() => {
  if (!attractions.value) return []
  if (!searchQuery.value) return attractions

  const query = searchQuery.value.toLowerCase()

  const filtered = attractions.value.filter(attraction =>
    attraction.title.toLowerCase().includes(query),
  )

  return filtered
})

// 선택 이벤트
const handleSelect = e => {
  console.log('Selection event:', e.detail)
  selectedValue.value = e.detail.value

  const data = attractions.value.find(
    attraction => attraction.id.toString() === e.detail.value,
  )?.title

  console.log('Selected data:', data)
  pickedAttraction.value = data
  open.value = false
}

// 검색한 데이터
const handleSearch = () => {
  console.log(searchQuery.value)
}
</script>

<template>
  <Popover v-model:open="open">
    <PopoverTrigger as-child>
      <Button
        variant="outline"
        role="combobox"
        :aria-expanded="open"
        class="flex items-center justify-start w-full"
      >
        <i class="text-sm text-gray-500 pi pi-map-marker"></i>
        {{
          value
            ? frameworks.find(framework => framework.value === value)?.label
            : 'Select framework...'
        }}
      </Button>
    </PopoverTrigger>
    <PopoverContent class="w-full p-0">
      <Command>
        <CommandInput class="h-9" placeholder="Search framework..." />
        <CommandEmpty>No framework found.</CommandEmpty>
        <CommandList>
          <CommandGroup>
            <CommandItem
              v-for="each in attractions"
              :key="each.id"
              :value="each.id"
              @select="
                ev => {
                  if (typeof ev.detail.value === 'string') {
                    value = ev.detail.value
                  }
                  open = false
                }
              "
            >
              {{ each.title }}
              <CheckIcon
                :class="
                  cn(
                    'ml-auto h-4 w-4',
                    value === each.id ? 'opacity-100' : 'opacity-0',
                  )
                "
              />
            </CommandItem>
          </CommandGroup>
        </CommandList>
      </Command>
    </PopoverContent>
  </Popover>
</template>
