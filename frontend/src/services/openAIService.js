import axios from 'axios'

const API_KEY = import.meta.env.VITE_OPENAI_API_KEY
const API_URL = 'https://api.openai.com/v1/chat/completions'

function delay(ms) {
  return new Promise(resolve => setTimeout(resolve, ms))
}

export const fetchChatGPTResponse = async (message, prompting, maxTokens) => {
  try {
    const body = {
      model: 'gpt-3.5-turbo', // 사용할 모델
      messages: [prompting, { role: 'user', content: message }],
      max_tokens: maxTokens,
    }

    const options = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${API_KEY}`,
      },
    }

    // 요청 전에 대기 시간 추가
    await delay(1000) // 1초 대기 (요청 간격 조정)

    const response = await axios.post(API_URL, body, options)

    return response.data.choices[0].message.content
  } catch (error) {
    console.error('OpenAI API 호출 에러:', error)
    throw error
  }
}
