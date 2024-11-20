import { onMounted, onUnmounted } from 'vue'

export function useInfiniteScroll(
  targetRef,
  callback,
  options = { threshold: 1 },
) {
  onMounted(() => {
    // 무한 스크롤 핸들러
    const infiniteScrollHandler = entries => {
      const target = entries[0]
      if (target.isIntersecting) {
        // 무한 스크롤 시 콜백 함수 호출
        callback()
      }
    }

    // 무한 스크롤 옵저버
    const observer = new IntersectionObserver(infiniteScrollHandler, options)

    // 무한 스크롤 타겟 옵저버 등록
    if (targetRef.value) {
      observer.observe(targetRef.value)
    }

    // 언마운트 시 옵저버 제거
    onUnmounted(() => {
      if (targetRef.value) {
        observer.unobserve(targetRef.value)
      }
    })
  })
}
