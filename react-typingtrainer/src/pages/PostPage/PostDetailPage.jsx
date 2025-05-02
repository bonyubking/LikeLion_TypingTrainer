import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { fetchPostById } from '../../api/api';
import styles from './postdetail.module.css';

function PostDetailPage() {
  const { postId } = useParams();
  const navigate = useNavigate();
  const [post, setPost] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const loadPost = async () => {
      try {
        const data = await fetchPostById(postId);
        console.log('상세 게시글 데이터:', data); // 데이터 구조 확인용
        setPost(data);
        setLoading(false);
      } catch (err) {
        setError('게시물을 불러오는데 실패했습니다.');
        setLoading(false);
        console.error('Error fetching post:', err);
      }
    };

    loadPost();
  }, [postId]);

  if (loading) {
    return <div>게시물을 불러오는 중...</div>;
  }

  if (error) {
    return <div>{error}</div>;
  }

  if (!post) {
    return <div>게시물을 찾을 수 없습니다.</div>;
  }

  return (
    <div className={styles.container}>
      <div className={styles.windowControls}>
        <span className={`${styles.circle} ${styles.red}`} />
        <span className={`${styles.circle} ${styles.yellow}`} />
        <span className={`${styles.circle} ${styles.green}`} />
      </div>      
      <div className={styles.detailBox}>
      <button className={styles.backButton} 
        onClick={() => navigate('/post')}>
      ← 목록으로
      </button>

        <h1 className={styles.title}>
          {post.title}
          </h1>
        <div className= {styles.content}>
          
          {post.content}
        </div>
      </div>

      <div className={styles.inputSection}>
      <input
        type="text"
        className={styles.textBox}
        placeholder="내용을 입력하세요"
      />
      <button className={styles.inputButton}>등록하기</button>
      </div>
    </div>
  );
}

export default PostDetailPage; 