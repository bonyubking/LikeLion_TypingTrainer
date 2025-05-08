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
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const postsPerPage = 10; // 한 페이지당 표시할 게시물 수

  useEffect(() => {
    (async () => {
      try {
        const data = await fetchPosts();
        setPosts(data);
        setTotalPages(Math.ceil(data.length / postsPerPage));
      } catch {
        setError('게시물 로드 실패');
      } finally {
        setLoading(false);
      }
    })();
  }, []);

  // 현재 페이지의 게시물만 필터링
  const getCurrentPosts = () => {
    const startIndex = (currentPage - 1) * postsPerPage;
    const endIndex = startIndex + postsPerPage;
    return posts.slice(startIndex, endIndex);
  };

  // 페이지 변경 핸들러
  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

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

        {getCurrentPosts().map(post => (
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
              <span>{post.nickname}</span>
              <div className={styles.dateColumn}>
                <span>{new Date(post.createdAt).toLocaleDateString()}</span>
              </div>
              <span>{post.commentCount}</span>
              <span>{post.viewCount}</span>
            </div>
          </div>
        ))}
      </div>

      {/* 페이지네이션 */}
      <div className={styles.pagination}>
        <button
          className={styles.pageButton}
          onClick={() => handlePageChange(currentPage - 1)}
          disabled={currentPage === 1}
        >
          이전
        </button>
        {[...Array(totalPages)].map((_, index) => (
          <button
            key={index + 1}
            className={`${styles.pageButton} ${currentPage === index + 1 ? styles.active : ''}`}
            onClick={() => handlePageChange(index + 1)}
          >
            {index + 1}
          </button>
        ))}
        <button
          className={styles.pageButton}
          onClick={() => handlePageChange(currentPage + 1)}
          disabled={currentPage === totalPages}
        >
          다음
        </button>
      </div>

      {/* 글 작성 버튼 */}
      <button
        className={styles.createButton}
        onClick={() => navigate(`/post/write`)}
      >
        글 작성하기
      </button>
    </div>
  );
}
