// src/components/ServerStatus.jsx
// 1. Mui에서 필요한 컴포넌트를 가져온다.
import { useState, useEffect } from 'react';
import { Card, CardContent, Typography, List, ListItem, ListItemText } from '@mui/material';

// 가까데이터 가져오기
import {mockSystemInfoResponse} from '../data/mock.js'; // 이제 안씀
import {mockRealtimeMetricsResponse} from '../data/mock.js'; // 이제 안씀

// 백엔드에서 가져온 데이터를 선언
import { fatchRealTimeMetrics } from '../api/client.js';

export default function ServerStatus( {server} ) {
    // 가져온 데이터에서 필요한 데이터만 가져오기
    // const info = mockSystemInfoResponse.data;
    // const metrics = mockRealtimeMetricsResponse.data;
    const { info } = server;

    // [1]. ServerState 컴포넌트가 스스로 관리 할 useEffect metrics를 만든다.
    const [ metrics, setMetrics ] = useState({});

    // [2]. useEffect를 사용하여 1초동안 실시간 데이터를 가져온다.
    useEffect(()=> {
        const getMetrics = async () => {
            if(!info.serviceId) return; // serviceId가 아무것도 안들어오면 아무것도 하지 않는다.
            try {
                const response = await fatchRealTimeMetrics(info.serviceId);
                if (response.data && response.data.code === "200") {
                    setMetrics(response.data.data);
                }
            } catch (error) {
                console.error(`${info.serviceId} 데이터 가져오기 실패`, error);
            }
        };
        // 페이지 첫 로딩때 무조건 한번은 호출한다.
        getMetrics();
        const intervalId = setInterval(getMetrics, 10000);

        // 컴포넌트가 화면에서 사라질때 타이머를 한번 정리한다.
        return () => clearInterval(intervalId);
    }, [info.serviceId]); // serviceId가 변경이 된다면 이 함수를 재실행한다.


    // 귀찮은건 미리 계산한다.
    const memoryUsageGB = ((metrics?.usedMemoryBytes ?? 0) / (1024 ** 3)).toFixed(2);
    const memoryTotalGB = ((info?.totalMemoryBytes ?? 0)  / (1024 ** 3)).toFixed(2);
    const diskUsageGB = ((metrics?.usedDiskBytes ?? 0)  / (1024 ** 3)).toFixed(2);
    const diskTotalGB = ((info?.totalDiskBytes ?? 0)  / (1024 ** 3)).toFixed(2);

    const cpuUsageFormatted = ((metrics?.cpuUsagePercent ?? 0).toFixed(2));

    // 화면에 보여주기
    return (
        // AS-IS
        // <>
        //     <div>
        //         <h2>{info.serviceId} : {info.serviceName}</h2>
        //         <p>{info.os}</p>
        //         <ul>
        //             <li>CPU 사용률 : {metrics.cpuUsagePercent}</li>
        //             <li>Memory 사용률 : {memoryUsageGB}GB / {memoryTotalGB}GB</li>
        //             <li>disk 사용률 : {diskUsageGB}GB / {diskTotalGB}GB</li>
        //         </ul>
        //     </div>
        // </>

        // TO-BE
        // 2. Card 컴포넌트로 전체를 감싸서 보기좋은 크기와 그림자를 만든다.
        <Card sx={{minWidth: 275, margin: 2, boxShadow: 3}}>
            <CardContent>
                {/** 3. Typography 컴포넌트로 텍스트에 디자인을 결정한다. */}
                <Typography variant='h5' component="div">
                    {info?.serviceName ?? '없음'} ({info?.serviceId ?? '없음'})
                </Typography>
                <Typography sx={{ mb: 1.5 }} color='text.secondary'>
                    OS: {info?.os ?? '없음'}
                </Typography>

                <List dense>
                    <ListItem>
                        <ListItemText primary="CPU 사용률" secondary={`${cpuUsageFormatted} %`}/>
                    </ListItem>
                    <ListItem>
                        <ListItemText primary="Memory 사용률" secondary={`${memoryUsageGB} GB / ${memoryTotalGB} GB`}/>
                    </ListItem>
                    <ListItem>
                        <ListItemText primary="Disk 사용률" secondary={`${diskUsageGB} GB / ${diskTotalGB} GB`}/>
                    </ListItem>
                </List>
            </CardContent>
        </Card>
    )
}