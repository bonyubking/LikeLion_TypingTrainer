const SERVER_URL = ProcessingInstruction.env.REACT_APP_SERVER_URL;

// 공통 fetch 요청 함수
const REQUEST_OPTIONS = {
    headers: {
        'Content-Type': 'application/json',
    },
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

// 사용자 로그인
/* 호출한 페이지에서 사용하는 방법
try{
    const res = await userLogin(id,password);
    console.log('로그인 성공', res);
    // 성공시 그 외 처리할 작업 
}catch(error){
    alert(error.message); // 에러 메세지 알림 
}*/
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

// 사용자 회원가입

// 