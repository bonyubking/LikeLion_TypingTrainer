import React, { useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { createPost } from '../../api/api';  // 예: App.js에서 UserContext 제공
import styles from './PostWritePage.module.css';

function PostWritePage() {
  const navigate = useNavigate();
  const userId = sessionStorage.getItem('userId');   // 로그인 정보
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!title.trim() || !content.trim()) {
      alert('제목과 내용을 모두 입력해주세요.');
      return;
    }
    setLoading(true);
    try {
      await createPost({
        userId,  // 서버에서 요구하는 키에 맞춰서
        title,
        content
      });
      // 작성 후 목록으로
      navigate('/post');
    } catch (err) {
      console.error(err);
      alert('글 작성에 실패했습니다.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className={styles.page}>
      <h2 className={styles.heading}>글 쓰기</h2>
      <form className={styles.form} onSubmit={handleSubmit}>
        <div className={styles.formGroup}>
          <label htmlFor="title">제목</label>
          <input
            id="title"
            type="text"
            value={title}
            onChange={e => setTitle(e.target.value)}
            className={styles.input}
            placeholder="제목을 입력하세요"
          />
        </div>
        <div className={styles.formGroup}>
          <label htmlFor="content">내용</label>
          <textarea
            id="content"
            value={content}
            onChange={e => setContent(e.target.value)}
            className={styles.textarea}
            rows={10}
            placeholder="내용을 입력하세요"
          />
        </div>
        <button
          type="submit"
          className={styles.submitBtn}
          disabled={loading}
        >
          {loading ? '작성 중...' : '작성 완료'}
        </button>
      </form>
    </div>
  );
}

export default PostWritePage;
