# ForD
노면, 기상정보를 이용한 자동차 안전운전 주의보서비스 구현
2021 한이음 프로젝트

![image](https://user-images.githubusercontent.com/68692949/148066853-28722d2b-b9f1-4e73-86a8-f68b720e2c80.png)


    
## 프로젝트 소개   
**지방 및 일반 도로의 안전운전을 위한 기존 ITS 대비 효과적인 C-ITS 핵심서비스 구현**

동절기, 야간 등 도로에서 결빙으로 인한 연쇄 추돌 사고는 교통 체증 및 2차 사고의
위험으로 이어진다.  
도로 중 결빙 발생 다발 지역인 지방도로, 터널 출입구, 교량 구간,
산기슭 도로, 그늘진 곡선 도로를 대상으로 C-ITS 관점 안전 운전 결빙 주의보 
애플리케이션을 제공하여 결빙으로 발생하는 사고를 미리 예방하고자 한다.   
노면/기상 상태를 
아두이노, 기상 API로 측정, 차량 운전자용 앱(GIS/맵 기반) 구현을 통해 
앱 사용운전자 간 양방향 V2V, 운전자와 아두이노 센서 간 V2I 통신으로 결빙으로부터 
운전자를 보호함에 있다.   
단방향 정보 제공이 아닌 운전자 신고 기능으로 확대된 양방향
서비스 커버리지를 제공하고 있다.  

**[도로인프라]**
- 취약 구간 노면 결빙정보를 센싱 및 날씨 데이터(기온변화)를 취합·분석하여 안전 운전 경보 여부 판단
- 실시간 정보 처리 : 노면 센서 이용 상태정보(온도, 결빙 등) 획득 + 기온변화 데이터(공개 기상자료) 연동

**[자동차]**
- 도로인프라에서 전송한 안전 운전 경보를 Head Up Display(HUD), 음성, 핸들 진동 등으로 안내 서비스
- 경보 내용 : 취약 지점 거리(500m, 200m, 발생 구역) 주의 안내 및 노면 결빙상태 등 정보

## 작품 소개 영상
```
[ForD소개영상](https://youtu.be/Jxsg_7_UuWs)
```

## 프로젝트 보고서
```
[ForD결과보고서]: https://www.hanium.or.kr/html/skin/doc.html?fn=20211124085908668.hwp&rs=/html/synap/
```

## 서비스 구성도

![image](https://user-images.githubusercontent.com/68692949/148065779-202680eb-4107-44b9-a466-a78abcf0d973.png)

## 시스템 구성도
![image](https://user-images.githubusercontent.com/68692949/148065753-0cf874c6-36aa-491a-b473-229c56270312.png)

## 시스템 흐름도
![image](https://user-images.githubusercontent.com/68692949/148065395-66313e07-7be0-4854-9e7c-35592f50ef79.png)

## 웹 구성도
![image](https://user-images.githubusercontent.com/68692949/148065812-d277600d-def5-47ed-9706-f5d853127277.png)

## 앱 구성도
![image](https://user-images.githubusercontent.com/68692949/148065865-a8cec92a-6c01-4781-a21b-0a948f3b2112.png)


## 앱 실행 이미지
![image](https://user-images.githubusercontent.com/68692949/148066701-d67210ce-496d-4d11-b809-d20c3ed553ca.png)
![image](https://user-images.githubusercontent.com/68692949/148066742-12f2d997-094c-44bf-99b9-e8b52f002d05.png)
