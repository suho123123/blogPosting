import React, { ChangeEvent, useEffect, useRef, useState } from 'react'
import './style.css'
import defaultProfileImage from 'assets/image/default-profile-image.png'
import { useNavigate, useParams } from 'react-router-dom';
import { BoardListItem } from 'types/interface';
import { latestBoardListMock } from 'mocks';
import BoardItem from 'components/BoardItem';
import { BOARD_PATH, BOARD_WRITE_PATH, MAIN_PATH, USER_PATH } from 'constant';
import { useLoginUserStore } from 'stores';
import { fileUploadRequest, getUserBoardListRequest, getUserRequest, patchNicknameRequest, patchProfileImageRequest } from 'apis';
import { GetUserResponseDto, PatchNicknameResponseDto, PatchProfileImageResponseDto } from 'apis/response/user';
import { ResponseDto } from 'apis/response';
import { PatchNicknameRequestDto, PatchProfileImageRequestDto } from 'apis/request/user';
import { Cookies, useCookies } from 'react-cookie';
import { usePagination } from 'hooks';
import { GetUserBoardListResponseDto } from 'apis/response/board';
import Pagination from 'components/Pagination';

// component: 유저 페이지 컴포넌트
export default function User() {

    // userEmail path variable 상태
    const {userEmail} = useParams();
    // state: 마이페이지 여부 상태
    const [isMyPage, setMyPage] = useState<boolean>(false);
    // state: 로그인 유저 상태
    const {loginUser} = useLoginUserStore();
    // state: 쿠키 상태
    const [cookies, setCookies] = useCookies();

    // function: 네비게이트 함수
    const navigate = useNavigate();

    // component: 유저 페이지 상단 컴포넌트
    const UserTop = () => {

        // state: 이미지 파일 인풋 참조 상태
        const imageInputRef = useRef<HTMLInputElement | null>(null);
        // state: 닉네임 변경 여부 상태
        const [isNicknameChange, setNicknameChange] = useState<boolean>(false);
        // state: 닉네임 상태
        const [nickname, setNickname] = useState<string>('');
         // state: 변경 닉네임 상태
        const [changeNickname, setChangeNickname] = useState<string>('');
        // state: 프로필 이미지 상태
        const [profileImage, setProfileImage] = useState<string | null>(null);

        // function: get user response 처리 함수
        const getUserResponse = (responseBody: GetUserResponseDto | ResponseDto | null) => {
            if(!responseBody) return;
            const {code} = responseBody;
            if(code === 'NU') alert('존재하지 않는 유저입니다.');
            if(code === 'DBE') alert('데이터베이스 오류입니다.');
            if(code !== 'SU') {
                navigate(MAIN_PATH())
                return;
            }

            const {email, nickname, profileImage} = responseBody as GetUserResponseDto;
            setNickname(nickname);
            setProfileImage(profileImage);
            const isMyPage = email === loginUser?.email;
            setMyPage(isMyPage);
        }
        // function: file upload response 처리 함수
        const fileUploadResponse = (profileImage: string | null) => {
            if(!profileImage) return;
            console.log("asdfasdfasdfadsf")
            if(!cookies.accessToken) return;
            console.log("adf")
            const requestBody: PatchProfileImageRequestDto = {profileImage};
            patchProfileImageRequest(requestBody, cookies.accessToken).then(patchProfileImageResponse);
        }
        // function: patch profile image response 처리 함수
        const patchProfileImageResponse = (responseBody: PatchProfileImageResponseDto | ResponseDto | null) => {
            if(!responseBody) return;
            const {code} = responseBody;
            if(code === 'AF') alert('인증에 실패했습니다.');
            if(code === 'NU') alert('존재하지 않는 유저입니다.');
            if(code === 'DBE') alert('데이터베이스 오류입니다.');
            if(code !== 'SU') return;

            if(!userEmail) return;
            getUserRequest(userEmail).then(getUserResponse);
        }
        // function: patch nickname response 처리 함수
        const patchNicknameResponse = (responseBody: PatchNicknameResponseDto | ResponseDto | null) => {
            if(!responseBody) return;
            const {code} = responseBody;
            if(code === 'VF') alert('닉네임은 필수입니다.');
            if(code === 'DN') alert('중복되는 닉네임입니다.');
            if(code === 'AF') alert('인증에 실패했습니다.');
            if(code === 'NU') alert('존재하지 않는 유저입니다.');
            if(code === 'DBE') alert('데이터베이스 오류입니다.');
            if(code !== 'SU') return;

            if(!userEmail) return;
            getUserRequest(userEmail).then(getUserResponse);
            setNicknameChange(false);
        }

        // event handler: nickname edit button click event
        const onNicknameEditButtonClickHandler = () => {
            if(!isNicknameChange) {
                setChangeNickname(nickname);
                setNicknameChange(!isNicknameChange);
                return;
            }

            if(!cookies.accessToken) return;
            const requestBody: PatchNicknameRequestDto = {
                nickname: changeNickname
            }
            patchNicknameRequest(requestBody, cookies.accessToken).then(patchNicknameResponse);
        }
        // event handler: profile box click event
        const onProfileBoxClickHandler = () => {
            if(!isMyPage) return; 
            if(!imageInputRef.current) return;
            imageInputRef.current.click();
        }
        // event handler: profile image change event
        const onProfileImageChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
            if(!event.target.files || !event.target.files?.length) return;

            const file = event.target.files[0];
            const data = new FormData();
            data.append('file', file);
            fileUploadRequest(data).then(fileUploadResponse)
        }
        // event handler: nickname change event
        const onNicknameChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
            const {value} = event.target;
            setChangeNickname(value);
        }

        // Effect: user email path variable 변경 시 실행할 함수
        useEffect(() => {
            if(!userEmail) return;
            getUserRequest(userEmail).then(getUserResponse);
        }, [userEmail])

        // render: 유저 화면 상단 컴포넌트 렌더링
        return (
            <div id='user-top-wrapper'>
                <div className='user-top-container'>
                    {isMyPage ? 
                    <div className='user-top-my-profile-image-box' onClick={onProfileBoxClickHandler}>
                        {profileImage !== null ?
                        <div className='user-top-profile-image' style={{backgroundImage: `url(${profileImage})`}}></div> :
                        <div className='icon-box-large'>
                            <div className='icon image-box-white-icon'></div>
                        </div>
                        }
                        <input ref={imageInputRef} type='file' accept='image/*' style={{display: 'none'}} onChange={onProfileImageChangeHandler} />
                    </div> :
                    <div className='user-top-profile-image-box' style={{backgroundImage: `url(${profileImage ? profileImage : defaultProfileImage})`}}></div>
                    }
                    <div className='user-top-info-box'>
                        <div className='user-top-info-nickname-box'>
                            {isMyPage ?
                            <>
                            {isNicknameChange ? 
                            <input className='user-top-info-nickname-input' type='text' size={nickname.length + 1} value={changeNickname} onChange={onNicknameChangeHandler} /> : 
                            <div className='user-top-info-nickname'>{nickname}</div>
                            }
                            <div className='icon-button' onClick={onNicknameEditButtonClickHandler}>
                                <div className='icon edit-icon'></div>
                            </div>
                            </> : 
                            <div className='user-top-info-nickname'>{nickname}</div>
                            }
                        </div>
                        <div className='user-top-info-email'>{'qwerty1@gmail.com'}</div>
                    </div>
                </div>
            </div>
        )
    }
    // component: 유저 페이지 하단 컴포넌트
    const UserBottom = () => { 

        // state: 페이지네이션 관련 상태
        const {currentPage, setCurrentPage, currentSection, setCurrentSection, 
        viewList, viewPageList, totalSection, setTotalList} = usePagination<BoardListItem>(5);
        // state: 게시물 개수 상태
        const [count, setCount] = useState<number>(2);
        // state: 게시물 리스트 상태(임시)
        const [userBoardList, setUserBoardList] = useState<BoardListItem[]>([]);

        // function: get user board list response 처리 함수
        const getUserBoardListResponse = (responseBody: GetUserResponseDto | ResponseDto | null) => {
            if(!responseBody) return;
            const {code} = responseBody;
            if(code === 'NU') {
                alert('존재하지 않는 유저입니다.');
                navigate(MAIN_PATH());
                return;
            }

            if(code === 'DBE') alert('데이터베이스 오류입니다.');
            if(code !== 'SU') return;

            const {userBoardList} = responseBody as GetUserBoardListResponseDto;
            setTotalList(userBoardList);
            setCount(userBoardList.length);
        }

        // event handler: 사이드 카드 클릭 이벤트 처리
        const onSideCardClickHandler = () => {
            if(isMyPage) navigate(BOARD_PATH() + '/' + BOARD_WRITE_PATH());
            else if(loginUser) navigate(USER_PATH(loginUser.email));
        }

        // effect: userEmail path variable이 변경 될 때마다 실행할 함수
        useEffect(() => {
            if(!userEmail) return;
            getUserBoardListRequest(userEmail).then(getUserBoardListResponse);
        }, [userEmail])

        // render: 유저 화면 하단 컴포넌트 렌더링
        return (
            <div id='user-bottom-wrapper'>
                <div className='user-bottom-container'>
                    <div className='user-bottom-title'>{isMyPage ? '내 게시물 ' : '게시물 '}<span className='emphasis'>{count}</span></div>
                    <div className='user-bottom-contents-box'>
                        {count === 0 ?
                        <div className='user-bottom-contents-nothing'>{'게시물이 없습니다.'}</div> : 
                        <div className='user-bottom-contents'>
                            {viewList.map(boardListItem => <BoardItem boardListItem={boardListItem} />)}
                        </div>
                        }
                        <div className='user-bottom-side-box'>
                            <div className='user-bottom-side-card' onClick={onSideCardClickHandler}>
                                <div className='user-bottom-side-container'>
                                    {isMyPage ? 
                                    <>
                                    <div className='icon-box'>
                                        <div className='icon edit-icon'></div>
                                    </div>
                                    <div className='user-bottom-side-text'>{'글쓰기'}</div>
                                    </> : 
                                    <>
                                    <div className='user-bottom-side-text'>{'내 게시물로 가기'}</div>
                                    <div className='icon-box'>
                                        <div className='icon arrow-right-icon'></div>
                                    </div>
                                    </>
                                    }
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className='user-bottom-pagination-box'>
                    {count !== 0 && 
                    <Pagination currentPage={currentPage} currentSection={currentSection} setCurrentPage={setCurrentPage}
                    setCurrentSection={setCurrentSection} viewPageList={viewPageList} totalSection={totalSection}/>}
                    </div>
                </div>
            </div>
        )
    }

    // component: 유저 페이지 컴포넌트 렌더링
    return (
        <>
        <UserTop />
        <UserBottom />
        </>
    )
}
