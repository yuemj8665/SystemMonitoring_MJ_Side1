// 1. useState, useEffect는 React 라이브러리에서 가져온다.
import { useState, useEffect } from 'react'
import './App.css'
import ServerStatus from './components/ServerStatus'
// 로딩 아이콘과 텍스트를 위해 MUI 컴포넌트를 추가로 가져온다.
import { Grid, Box, CircularProgress, Typography } from '@mui/material';
// mockServers는 이제 API 응답 데이터처럼 사용된다.
// import { mockServers } from './data/mock.js';

// setTimeout 대신 우리가 만든 API 호출 함수를 가져온다.
import { fatchServers } from './api/client';

export default function App() {
  // 2. useState를 사용하여 상태를 두 가지 만든다.
  // - server: 서버목록 데이터를 담을 상태, 처음에는 비어있는 배열
  // - loading: 로딩중인지 여부를 담을 상태, 처음에는 로딩중임으로 true.  
  const [servers, setServers] = useState([]);
  const [loading, setLoading] = useState(true);

  // 3. useEffect를 사용하여, 컴포넌트가 처음 화면에 그려질 때, 단 한번만 실행되는 코드를 작성한다.
  useEffect(() => {
    // async/await문법으로 비동기 코드를 더 깔끔하게 작성 가능
    const getServerData = async () => {
      try {
        // 1. API 호출
        const response = await fatchServers();
        // 응답 데이터 중 실제 서버 목록 데이터(response.data.data)를 상태에 저장
        if (response.data.data && String (response.data.code) === "200") {
          setServers(response.data.data);
      }
    } catch (error) {
      console.error("서버 데이터를 가져오는데 실패했습니다.", error);
    } finally {
      // 3. API 호출에 성공하든 실패하든 로딩 상태를 끝낸다.
      setLoading(false);
    }

    // // API 호출을 시뮬레이션 하는 로직
    // console.log("데이터 가져오기 시작"); // 콘솔에서 확인하기
    // const timer = setTimeout(() => {
    //   console.log("2초 뒤 데이터 가져오기 완료");
    //   // 2초 뒤 받아온 데이터를 server 상태에 저장한다
    //   setServers(mockServers);
    //   // 로딩은 끝났다.
    //   setLoading(false);
    // }, 2000); // 2초 뒤에 데이터 가져오기 완료
    // return () => clearTimeout(timer); // 타이머 정리
    }
    getServerData();
  }, []); // 두번째 인자로 빈 배열을 넣어주면 단 한번만 실행이 된다. 

  // 4. 로딩 상태에 따라 다른 UI를 렌더링한다.
  if (loading) {
    return (
      <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
        <CircularProgress />
        <Typography variant="h6" sx={{ marginLeft: 2 }}>
          데이터를 불러오는 중입니다...
        </Typography>
      </Box>
    )
  }
    // 5. 로딩이 끝나면 서버 목록을 렌더링한다.
    return (
      <Box sx={{ padding: 2 }}>
        <h1>시스템 모니터링 대시보드</h1>
        <hr/>
        {/** Grid 컨테이너로 전체를 감싼다 */}
        <Grid container spacing={2}>
          {/** mockServers 배열을 map() 함수로 순회하면서 각각의 server데이터를
           * ServerStatus 컴포넌트에 props로 넘겨준다
           * 이때 React는 각각의 컴포넌트를 구분하기윟 고유한 'key'를 구분으로 한다.
           * 우리는 ServiceId를 key로 사용할 것
           */}
          { servers.length > 0 ? (
          servers.map((server) => (
            <Grid item xs={12} sm={6} md={4} key={server.serviceId}>
              <ServerStatus server={{info: server, metrics: {} }}/>
              </Grid>
          ))
         ) : (
           !loading && <Typography sx={{ p:2 }}>표시할 서버 데이터가 없습니다.</Typography>
          )}
        </Grid>
      </Box>
    )
}



