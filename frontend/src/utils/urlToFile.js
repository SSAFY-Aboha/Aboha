/**
 * URL 이미지를 File 객체로 변환하는 함수
 * @param {string} url - 이미지 URL
 * @param {string} fileName - 생성할 파일 이름 (확장자 포함)
 * @returns {Promise<File>} File 객체
 */
async function urlToFile(url, fileName) {
  try {
    // 1. URL에서 이미지 가져오기
    const response = await fetch(url)
    // 2. Blob으로 변환
    const blob = await response.blob()
    // 3. File 객체 생성
    const file = new File([blob], fileName, { type: blob.type })
    return file
  } catch (error) {
    console.error('URL to File 변환 실패:', error)
    throw error
  }
}

export default urlToFile
