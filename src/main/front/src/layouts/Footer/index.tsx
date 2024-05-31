import React from 'react'
import './style.css'

// component footer 레이아웃
export default function Footer() {

    // event handler: insta icon 버튼 클릭 이벤트 처리
    const onInstaIconButtonClickHandler = () => {
        window.open('https://www.instagram.com');
    };

    // event handler: facebook icon 버튼 클릭 이벤트 처리
    const onFacebookIconButtonClickHandler = () => {
        window.open('https://www.facebook.com');
    };

    // component footer 레이아웃 렌더링
    return (
        <div id='footer'>
            <div className='footer-container'>
                <div className='footer-top'>
                    <div className='footer-logo-box'>
                        <div className='icon-box'>
                            <div className='icon logo-light-icon'></div>
                        </div>
                        <div className='footer-logo-text'>{'SUHO Board'}</div>
                    </div>
                    <div className='footer-link-box'>
                        <div className='footer-email-link'>{'qwerty123@gmail.com'}</div>
                        <div className='icon-button' onClick={onInstaIconButtonClickHandler}>
                            <div className='icon insta-icon'></div>
                        </div>
                        <div className='icon-button' onClick={onFacebookIconButtonClickHandler}>
                            <div className='icon facebook-icon'></div>
                        </div>
                    </div>
                </div>
                <div className='footer-bottom'>
                    <div className='footer-copyright'>{'Copyright © 2024 Suho. All Rights Reserved.'}</div>
                </div>
            </div>
        </div>
    )
}
