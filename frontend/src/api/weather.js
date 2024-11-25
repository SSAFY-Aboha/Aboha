import { fetchApi } from '@/utils/api-util'

const OPEN_WEATHER_API_KEY = import.meta.env.VITE_OPEN_WEATHER_API_KEY
const OPEN_WEATHER_API_URL =
  'http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst'

const params = {
  serviceKey: OPEN_WEATHER_API_KEY,
  pageNo: 1,
  numOfRows: 10,
  dataType: 'JSON',
  base_date: '20241125',
  base_time: '0600',
  nx: 55,
  ny: 127,
}

// 날씨 정보 조회
const getWeatherInfo = (nx, ny) => {
  return fetchApi('get', OPEN_WEATHER_API_URL, {
    ...params,
    nx,
    ny,
  })
}

export { getWeatherInfo }
