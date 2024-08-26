package com.javalab.humaninfo.service;

import com.javalab.humaninfo.entity.Human;
import com.javalab.humaninfo.repository.HumanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class HumanService {

    private final HumanRepository humanRepository;

    private static final String[] SURNAMES = {"김", "이", "박", "최", "정", "강", "조", "윤", "장", "임",
            "한", "오", "서", "신", "권", "황", "안", "송", "전", "홍",
            "유", "고", "문", "양", "손", "배", "백", "차", "나", "남",
            "심", "노", "허", "구", "성", "주", "우", "임", "진", "지",
            "강", "변", "여", "하", "류", "고", "민", "탁", "엄", "구"};
    private static final String[] NAME_CHARS =  {"민", "서", "지", "예", "주", "윤", "지", "도", "하", "유", "진", "선", "우", "현",
            "영", "미", "성", "준", "원", "혜","희","자"};
    private static final String[] CITIES = {"서울","LA","뉴욕", "오사카", "교토", "도쿄", "베이징", "상해", "텐진", "여주", "여수",
            "광양", "삼천포", "나주", "부산", "인천", "대구", "대전", "광주", "울산", "수원", "창원", "고양", "용인", "성남", "부천",
            "청주", "화성", "전주", "천안", "남양주", "안산", "안양", "시흥", "김포", "파주", "광명", "구리", "양주", "의정부", "포천",
            "하남", "마포", "송파", "서초", "동작", "금천", "중랑", "성북", "도봉", "은평", "평양", "함흥", "남포", "신의주", "원산",
            "강계", "청진", "개성", "사리원", "해주", "신흥", "평성", "라선", "김책", "황주", "만포", "성진", "안주", "동관", "목포",
            "진주", "포항", "경주", "영주", "안동", "울진", "양산", "진해", "김해", "거제", "창녕", "밀양", "남해", "통영", "구미",
            "경산", "상주", "예천", "의성", "영천", "고령", "봉화", "영양", "울릉", "동해", "삼척", "횡성", "원주", "태백", "강릉", "속초", "양양", "홍천", "철원", "화천", "양구", "인제", "고성"};

    private static final String[] JOBS = {
            "교사", "의사", "간호사", "엔지니어", "프로그래머", "디자이너", "회계사", "변호사", "경찰관", "소방관",
            "요리사", "운전기사", "판매원", "마케터", "기자", "작가", "배우", "가수", "화가", "사진작가",
            "건축가", "인테리어디자이너", "패션디자이너", "헤어디자이너", "메이크업아티스트", "운동선수", "코치", "트레이너", "영양사", "약사",
            "수의사", "농부", "어부", "목수", "전기기술자", "배관공", "용접공", "기계공", "택시운전사", "버스운전사",
            "파일럿", "승무원", "호텔리어", "여행가이드", "통역사", "번역가", "학원강사", "과학자", "연구원", "대학교수"
    };
    private static final String[] COMPANIES =  {
            "삼성전자", "LG전자", "SK하이닉스", "현대자동차", "기아", "삼성디스플레이", "삼성SDI", "LG화학",
            "포스코", "한화그룹", "현대중공업", "SK이노베이션", "LG생활건강", "한국전력공사", "롯데케미칼", "SK텔레콤",
            "아모레퍼시픽", "현대제철", "CJ제일제당", "대우건설", "삼성물산", "현대건설", "LG유플러스", "농심", "신한금융지주",
            "하나금융지주", "KB금융지주", "SK바이오팜", "삼성바이오로직스", "셀트리온", "카카오", "네이버", "엔씨소프트",
            "넥슨", "SK머티리얼즈", "롯데쇼핑", "한국타이어", "대림산업", "현대리바트", "삼성중공업", "두산중공업", "S-OIL",
            "대한항공", "아시아나항공", "현대모비스", "한솔제지", "LG디스플레이", "SKC", "SK네트웍스", "GS칼텍스", "동국제강",
            "현대건설기계", "삼성전기", "삼성SDS", "롯데제과", "한국조선해양", "LG이노텍", "CJ대한통운", "이마트", "현대백화점",
            "GS리테일", "롯데마트", "LotteDutyFree", "한국가스공사", "한진칼", "동원F&B", "KT", "KT&G", "현대오토에버",
            "LG하우시스", "LS산전", "현대위아", "동부건설", "대우인터내셔널", "한화솔루션", "한화에어로스페이스", "삼성엔지니어링",
            "삼성생명", "현대상선", "현대중공업지주", "카카오뱅크", "삼성카드", "신세계", "롯데홈쇼핑", "롯데건설", "CJCGV",
            "두산", "한국제지", "대한제강", "이마트24", "CJENM", "한국산업은행", "신세계백화점", "현대하이닉스", "SK바이오사이언스",
            "금호석유화학", "한화생명", "한화호텔앤드리조트", "푸르밀", "STX", "한국동서발전", "포스코인터내셔널", "하나투어", "한국철강",
            "KTCS", "네오위즈", "세아제강", "한화큐셀", "현대카드", "GS건설", "KCC", "현대일렉트릭", "넥슨코리아", "동아제약", "삼성증권",
            "카카오게임즈", "포스코케미칼", "현대엘리베이터", "현대오일뱅크", "SM엔터테인먼트", "YG엔터테인먼트", "JYP엔터테인먼트", "HYBE",
            "CJENM", "카카오엔터테인먼트", "FNC엔터테인먼트", "큐브엔터테인먼트", "플레디스엔터테인먼트", "스타쉽엔터테인먼트", "RBW",
            "울림엔터테인먼트", "판타지오", "DSP미디어", "WM엔터테인먼트", "브레이브엔터테인먼트", "빅히트뮤직", "젤리피쉬엔터테인먼트",
            "안테나", "PNATION", "KB국민은행", "신한은행", "하나은행", "우리은행", "NH농협은행", "IBK기업은행", "SC제일은행", "씨티은행",
            "KDB산업은행", "카카오뱅크", "케이뱅크", "토스뱅크", "부산은행", "대구은행", "광주은행", "전북은행", "제주은행", "경남은행", "수협은행",
            "새마을금고", "미래에셋증권", "NH투자증권", "삼성증권", "KB증권", "한국투자증권", "신한금융투자", "메리츠증권", "대신증권", "하나금융투자",
            "유안타증권", "키움증권", "SK증권", "DB금융투자", "교보증권", "현대차증권", "이베스트투자증권", "KTB투자증권", "BNK투자증권", "한화투자증권",
            "케이프투자증권"
    };
    private static final String[] EDUCATION_LEVELS =  {"고졸", "대졸", "대학원졸"};
    private static final String[] MBTI_TYPES = {"ISTJ", "ISFJ", "INFJ", "INTJ", "ISTP", "ISFP", "INFP", "INTP", "ESTP", "ESFP", "ENFP", "ENTP", "ESTJ", "ESFJ", "ENFJ", "ENTJ"};
    private static final String[] BLOOD_TYPES = {"A", "B", "O", "AB"};
    private static final String[] HOBBIES = {
            "독서", "영화감상", "음악감상", "요리", "등산", "캠핑", "낚시", "사진촬영", "그림그리기", "운동",
            "요가", "명상", "악기연주", "노래부르기", "춤", "보드게임", "퍼즐", "체스", "바둑", "카드게임",
            "볼링", "탁구", "테니스", "골프", "수영", "자전거타기", "조깅", "헬스", "요리대회참가", "베이킹",
            "가드닝", "애완동물키우기", "여행", "블로깅", "유튜브제작", "게임", "외국어학습", "DIY", "서예",
            "도예", "목공예", "뜨개질", "자수", "퀼트", "종이접기", "칼리그래피", "마술", "저글링", "댄스",
            "스케이트보드", "서핑", "스키", "스노보드", "클라이밍", "요트", "승마", "배드민턴", "양궁", "필라테스"
    };
    private static final Random RANDOM = new Random();

    @Transactional
    public void generateAndSaveToSqlFileAndDatabase(int count) throws IOException {
        List<Human> humans = new ArrayList<>();

        try (FileWriter writer = new FileWriter("humans.sql")) {
            writer.write("INSERT INTO human (name, gender, address, phone_number, job, company, education, hobby, mbti, blood_type, age, salary, wealth) VALUES\n");

            for (int i = 0; i < count; i++) {
                Human human = generateHuman();
                humans.add(human);

                String sqlValues = String.format("('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', %d, %d, %d)",
                        human.getName(), human.getGender(), human.getAddress(), human.getPhoneNumber(),
                        human.getJob(), human.getCompany(), human.getEducation(), human.getHobby(),
                        human.getMbti(), human.getBloodType(), human.getAge(), human.getSalary(), human.getWealth());

                writer.write(sqlValues);
                if (i < count - 1) {
                    writer.write(",\n");
                } else {
                    writer.write(";\n");
                }
            }
            System.out.println("SQL 파일이 성공적으로 생성되었습니다: humans.sql");
        }

        // 데이터베이스에 저장
        humanRepository.saveAll(humans);
        System.out.println(count + "개의 Human 데이터가 데이터베이스에 저장되었습니다.");
    }

    private Human generateHuman() {
        String name = generateName();
        String gender = RANDOM.nextBoolean() ? "남성" : "여성";
        String address = CITIES[RANDOM.nextInt(CITIES.length)];
        String phoneNumber = generatePhoneNumber();
        String job = JOBS[RANDOM.nextInt(JOBS.length)];
        String company = COMPANIES[RANDOM.nextInt(COMPANIES.length)];
        String education = EDUCATION_LEVELS[RANDOM.nextInt(EDUCATION_LEVELS.length)];
        String hobby = HOBBIES[RANDOM.nextInt(HOBBIES.length)];
        String mbti = MBTI_TYPES[RANDOM.nextInt(MBTI_TYPES.length)];
        String bloodType = BLOOD_TYPES[RANDOM.nextInt(BLOOD_TYPES.length)];
        int age = RANDOM.nextInt(32) + 18;
        int salary = generateSalary(job);
        long wealth = generateWealth();

        return new Human(null, name, gender, address, phoneNumber, job, company, education, hobby, mbti, bloodType, age, salary, wealth);
    }
    private String generateName() {
        return SURNAMES[RANDOM.nextInt(SURNAMES.length)] +
                NAME_CHARS[RANDOM.nextInt(NAME_CHARS.length)] +
                NAME_CHARS[RANDOM.nextInt(NAME_CHARS.length)];
    }

    private String generateGender() {
        return RANDOM.nextBoolean() ? "남성" : "여성";
    }

    private String generateAddress() {
        return CITIES[RANDOM.nextInt(CITIES.length)];
    }

    private String generatePhoneNumber() {
        return String.format("010-%04d-%04d", RANDOM.nextInt(10000), RANDOM.nextInt(10000));
    }

    private String generateJob() {
        return JOBS[RANDOM.nextInt(JOBS.length)];
    }

    private String generateCompany() {
        return COMPANIES[RANDOM.nextInt(COMPANIES.length)];
    }

    private String generateEducation() {
        return EDUCATION_LEVELS[RANDOM.nextInt(EDUCATION_LEVELS.length)];
    }

    private String generateHobby() {
        return HOBBIES[RANDOM.nextInt(HOBBIES.length)];
    }

    private String generateMBTI() {
        return MBTI_TYPES[RANDOM.nextInt(MBTI_TYPES.length)];
    }

    private String generateBloodType() {
        return BLOOD_TYPES[RANDOM.nextInt(BLOOD_TYPES.length)];
    }

    private int generateAge() {
        return RANDOM.nextInt(32) + 18;
    }

    private int generateSalary(String job) {
        // 직업에 따라 급여 범위를 다르게 설정할 수 있습니다.
        // 여기서는 간단한 예시로 모든 직업에 대해 동일한 범위를 사용합니다.
        return 3000 + RANDOM.nextInt(3001); // 3000 ~ 6000 사이의 값
    }

    private long generateWealth() {
        if (RANDOM.nextDouble() < 0.9) {
            return RANDOM.nextLong(20000 + 1);
        } else {
            return 20000 + RANDOM.nextLong(980000L + 1);
        }
    }
}

