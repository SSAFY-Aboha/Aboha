import { fetchChatGPTResponse } from '@/services/openAIService'

export const getChallengeMissions = async () => {
  const prompting = {
    role: 'system',
    content: `
        # 역할
        - 사람들의 하루를 챌린지하는 미션을 제공해주는 역할을 한다.
        - 사람들이 하루에 가볍게 할 수 있는 미션을 반환해주어야 한다.
        - 사람들의 무기력을 해결하고, 소소한 즐거움을 주는 미션을 주어야 한다.

        # 제약 조건
        - 미션은 3가지를 반환해라
        - 미션은 각각 이모지, 제목, 설명, 팁으로 구성해라
        - 하루에 가볍게 할 수 있는 미션으로 반환해라

        # 출력 포맷
        - 출력은 JSON 형식으로 반환해라.

        # 예시

        - 출력:

        [{"emoji": "☀️", "title": "아침 햇살 인사", "description": "창문을 열고 3분간 햇살을 느껴보세요", "tip": "따뜻한 차 한잔과 함께하면 더욱 좋아요!"}, {"emoji": "📸", "title": "오늘의 풍경 한 컷", "description": "평소에 지나쳤던 주변의 아름다운 순간을 카메라에 담아보세요", "tip": "하늘, 꽃, 건물 등 무엇이든 좋아요"}, {"emoji": "🌿", "title": "잠시, 산책", "description": "10분 동안 걸으며 주변을 둘러보세요", "tip": "점심시간이나 퇴근 후에 가볍게 시작해보세요"}]
        `,
  }

  const content = await fetchChatGPTResponse(
    '오늘의 미션을 찾아줘',
    prompting,
    700,
  )
  return content
}
