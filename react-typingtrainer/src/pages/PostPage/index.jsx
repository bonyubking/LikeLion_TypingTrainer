// src/pages/PostPage/PostPage.jsx
import React, { useEffect, useState } from 'react';
import { useNavigate }                  from 'react-router-dom';
import { fetchPosts }                   from '../../api/api';
import styles                           from './post.module.css';
import PostWritePage from './PostWritePage';

export default function PostPage() {
  
  const navigate = useNavigate();
  const [posts, setPosts]     = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError]     = useState(null);

  useEffect(() => {
    (async () => {
      try {
        const data = await fetchPosts();
        setPosts(data);
      } catch {
        setError('게시물 로드 실패');
      } finally {
        setLoading(false);
      }
    })();
  }, []);

  if (loading) return <div className={styles.container}>로딩 중…</div>;
  if (error)   return <div className={styles.container}>{error}</div>;

  return (
    <div className={styles.container}>
      {/* 헤더 */}
      <div className={styles.header}>
        <h1 className={styles.headerTitle}>자유 게시판</h1>
        <p className={styles.headerDesc}>유저들과 자유로운 의견을 주고 받아 보세요</p>
      </div>

      {/* 리스트 박스 */}
      <div className={styles.listBox}>
        {/* 헤더 추가 */}
        <div className={styles.listHeader}>
          <div className={styles.leftContent}>
            <div className={styles.itemIcon} />
            <div className={styles.itemText}>제목</div>
          </div>
          <div className={styles.itemMeta}>
            <span>작성자</span>
            <div className={styles.dateColumn}>
              <span>작성 시간</span>

            </div>
            <span>댓글</span>
            <span>조회</span>
          </div>
        </div>

        {posts.map(post => (
          <div
            key={post.postId}
            className={styles.item}
            onClick={() => navigate(`/post/${post.postId}`)}
          >
            {/* 왼쪽: 아이콘 + 제목 */}
            <div className={styles.leftContent}>
              <div className={styles.itemIcon} />
              <div className={styles.itemText}>{post.title}</div>
            </div>

            {/* 오른쪽: 메타 정보 */}
            <div className={styles.itemMeta}>
              <span>{post.uid}</span>
              <div className={styles.dateColumn}>
                <span>{new Date(post.createdAt).toLocaleDateString()}</span>
                <span>{new Date(post.createdAt).toLocaleTimeString()}</span>
              </div>
              <span>{post.commentCount}</span>
              <span>{post.viewCount}</span>
            </div>
          </div>
        ))}
      </div>
      {/* 3) 글 작성 버튼 */}
      <button
        className={styles.createButton}
        onClick={() => navigate(`/post/write`)}
      >
        글 작성하기
      </button>
    </div>
  );
}
