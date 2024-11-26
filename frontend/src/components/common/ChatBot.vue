<script setup>
import { ref } from 'vue'

const isOpen = ref(false)
const messages = ref([
  {
    id: 1,
    text: '안녕하세요! 오늘도 특별한 하루를 만들어보세요 ✨\n다음 중 어떤 도움이 필요하신가요?',
    isBot: true,
    options: [
      '오늘의 미션 받기 🎯',
      '주변 숨은 명소 찾기 🗺',
      '날씨에 맞는 활동 추천 ⛅',
      '일상 여행 팁 받기 💡',
    ],
  },
])
const newMessage = ref('')
const isTyping = ref(false)
const messageContainer = ref(null)

const toggleChat = () => {
  isOpen.value = !isOpen.value
}

const scrollToBottom = () => {
  if (messageContainer.value) {
    setTimeout(() => {
      messageContainer.value.scrollTop = messageContainer.value.scrollHeight
    }, 0)
  }
}

const sendMessage = (optionText = null) => {
  const messageText = optionText || newMessage.value.trim()
  if (!messageText) return

  messages.value.push({
    id: messages.value.length + 1,
    text: messageText,
    isBot: false,
  })
  scrollToBottom()

  if (!optionText) {
    newMessage.value = ''
  }

  isTyping.value = true
  scrollToBottom()

  setTimeout(() => {
    isTyping.value = false
    const botResponse = getBotResponse(messageText)
    messages.value.push({
      id: messages.value.length + 1,
      text: botResponse.text,
      isBot: true,
      options: botResponse.options || [],
    })
    scrollToBottom()
  }, 1000)
}

const getBotResponse = message => {
  const responses = {
    '오늘의 미션 받기 🎯': {
      text: '오늘의 미션이에요!\n- 평소와 다른 출근길 경로 택하기\n- 점심시간에 새로운 카페 방문하기\n- 퇴근 후 10분 산책하기\n\n어떠한 도움이 필요하신가요?',
      options: ['미션 완료했어요!', '다른 미션 받기'],
    },
    '주변 숨은 명소 찾기 🗺': {
      text: '주변에 숨은 명소를 찾아보세요!\n- 주변 공원 방문하기\n- 주변 산책로 산책하기\n- 주변 해변가 방문하기\n\n어떠한 도움이 필요하신가요?',
      options: ['명소 찾기 성공!', '다른 명소 찾기'],
    },
    '날씨에 맞는 활동 추천 ⛅': {
      text: '날씨에 맞는 활동을 추천해드릴게요!\n- 날씨가 좋을 때 등산하기\n- 날씨가 더울 때 수영장 방문하기\n- 날씨가 비올 때 카페에서 커피 마시기\n\n어떠한 도움이 필요하신가요?',
      options: ['활동 추천 받기', '다른 활동 추천 받기'],
    },
    '일상 여행 팁 받기 💡': {
      text: '일상 여행 팁을 추천해드릴게요!\n- 여행 전 필수품 준비하기\n- 여행 �� 모험을 추구하기\n- 여행 후 추억을 기록하기\n\n어떠한 도움이 필요하신가요?',
      options: ['팁 받기', '다른 팁 받기'],
    },
  }

  return (
    responses[message] || {
      text: '죄송합니다. 아직 학습 중입니다.',
      options: [],
    }
  )
}
</script>

<template>
  <div class="fixed z-50 bottom-5 left-5">
    <!-- 챗봇 버튼 -->
    <button
      class="text-2xl text-white transition-transform duration-300 bg-green-500 border-none rounded-full shadow-lg cursor-pointer w-14 h-14 hover:scale-110"
      @click="toggleChat"
    >
      <span v-if="!isOpen">💬</span>
      <span v-else>&times;</span>
    </button>

    <!-- 챗봇 창 -->
    <div
      v-show="isOpen"
      class="absolute bottom-20 left-0 w-[400px] h-[600px] bg-white rounded-lg shadow-lg flex flex-col"
    >
      <div class="p-4 text-white bg-green-500 rounded-t-lg">
        <h3 class="m-0 text-base">아보하 챗봇</h3>
      </div>

      <div ref="messageContainer" class="flex-1 p-4 overflow-y-auto">
        <div
          v-for="message in messages"
          :key="message.id"
          :class="[
            'my-2 p-3 rounded-2xl max-w-[80%] break-words transition-all duration-300',
            'animate-message',
            message.isBot
              ? 'bg-gray-100 self-start'
              : 'bg-green-500 text-white ml-auto',
          ]"
        >
          <div class="whitespace-pre-line">{{ message.text }}</div>
          <!-- 옵션 버튼들 추가 -->
          <div v-if="message.options?.length" class="mt-3 space-y-2">
            <button
              v-for="option in message.options"
              :key="option"
              @click="sendMessage(option)"
              class="block w-full px-3 py-2 text-sm text-left text-gray-700 transition-colors bg-white border border-gray-200 rounded-lg hover:bg-gray-50"
            >
              {{ option }}
            </button>
          </div>
        </div>

        <!-- 타이핑 표시기 -->
        <div
          v-if="isTyping"
          class="my-2 p-3 bg-gray-100 rounded-2xl max-w-[80%] self-start animate-message"
        >
          <div class="flex gap-1">
            <div class="w-2 h-2 bg-gray-400 rounded-full animate-bounce"></div>
            <div
              class="w-2 h-2 bg-gray-400 rounded-full animate-bounce"
              style="animation-delay: 0.2s"
            ></div>
            <div
              class="w-2 h-2 bg-gray-400 rounded-full animate-bounce"
              style="animation-delay: 0.4s"
            ></div>
          </div>
        </div>
      </div>

      <div class="flex gap-2 p-4 border-t border-gray-200">
        <input
          v-model="newMessage"
          @keyup.enter="sendMessage"
          placeholder="메시지를 입력하세요..."
          class="flex-1 p-2 border border-gray-300 rounded focus:outline-none focus:border-green-500"
        />
        <button
          @click="sendMessage"
          class="px-4 py-2 text-white transition-colors duration-300 bg-green-500 rounded hover:bg-green-600"
        >
          전송
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.animate-message {
  animation: slideIn 0.3s ease-out;
  opacity: 0;
  animation-fill-mode: forwards;
}

@keyframes slideIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>
