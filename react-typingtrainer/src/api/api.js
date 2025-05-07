const SERVER_URL = process.env.REACT_APP_HTTP_URL;
const CHAT_URL = process.env.REACT_APP_CHAT_URL;


/*
API 요청 함수 생성
- 설정할 것 : 메소드명, request_url, body(선택), method
export const 메소드명 = async () => {
    const request_url = `${SERVER_URL}/~~`; // api url 
    const body = {
        서버에서 받을 인자이름: 값,
    } // post 요청일 경우 사용. GET이면 필요없음
    
    const response = await fetch(request_url, {
        method: 'GET' 또는 'POST' 또는 'PUT',
        body, <- GET이면 없어도 됨. POST면 있어야됨. 
        ...REQUEST_OPTIONS, <- 공통 옵션. 꼭 필요함 
    });
    return handleResponse(response);  <- 호출한 페이지로 응답을 보냄
}

호출한 곳에서 사용하는 경우 
- try~catch문 이용
- 예시
const 컴포넌트에서쓰일메소드명 = async () => {
      try {
        const chatData = await API요청메소드명();
        //그외 데이터 처리 
      } catch (error) {
        console.error('채팅 데이터 로드 실패:', error);
      }
    };
*/

// 공통 fetch 요청 함수
const REQUEST_OPTIONS = {
    headers: {
        'Content-Type': 'application/json',
    },
    mode: 'cors' // CORS 모드 설정
};

// 응답 핸들링 함수
async function handleResponse(response) { 
    if (!response.ok) { 
        const errorResponse = await response.json();
        const error = new Error(errorResponse.message || '서버 요청 실패');
        error.status = errorResponse.status;
        throw error;
    }
    return response.json();
}

// 최근 채팅 30건 조회
export const getChats = async () => {
    const request_url = `${SERVER_URL}/chat`; // api url 

        const response = await fetch(request_url, {
            method: 'GET',
            ...REQUEST_OPTIONS,
        });

    return handleResponse(response); 
}


export async function fetchTypingRecords(queryString = '') {
    const qs = queryString ? `?${queryString}` : '';
    const url = `${SERVER_URL}/typing-records${qs}`;
  
    const response = await fetch(url, {
        method: 'GET',
        ...REQUEST_OPTIONS
    });

    const data = await handleResponse(response);
    
    // 배열이 아닌 경우를 대비해 안전하게 처리
    if (!Array.isArray(data)) {
        console.warn('서버 응답이 배열이 아닙니다:', data);
        return [];
    }
    
    return data;
}

export async function fetchSongRecords(queryString = '') {
    
    const qs = queryString ? `?${queryString}` : '';
    const url = `${SERVER_URL}/song-records${qs}`;

    const response = await fetch(url, {
        method: 'GET',
        ...REQUEST_OPTIONS
    });

    const data = await handleResponse(response);

    if (!Array.isArray(data)) {
        console.warn('서버 응답이 배열이 아닙니다:', data);
        return [];
    }

    
    return data;
}
// 사용자 로그인
/* 호출한 페이지에서 사용하는 방법
try{
    const res = await userLogin(id,password);
    console.log('로그인 성공', res);
    // 성공시 그 외 처리할 작업 
}catch(error){
    alert(error.message); // 에러 메세지 알림 
}*/
/*
export const userLogin = async (id, password) => {
    const request_url = `${SERVER_URL}/~~~`; // 서버에 보낼 요청 api 
    const body = { // 서버에 보낼 정보 
        id: id,
        password:password
    };
    // 서버에 request_url로 데이터를 전송 후 응답 받음 
    const response = await fetch(request_url, {
        method: 'POST', 
        body, // GET 요청일 경우 해당 부분 생략 가능
        ...REQUEST_OPTIONS,
    });

    //호출한 페이지로 데이터 반환
    return handleResponse(response); 
}
*/