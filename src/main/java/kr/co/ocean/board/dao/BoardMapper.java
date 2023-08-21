package kr.co.ocean.board.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ocean.board.dto.BoardVO;

@Mapper
public interface BoardMapper {
	/**
	 * 게시글 추가 Mapper
	 * @param boardVO 객체 {@code BoardVO}
	 * @throws Exception
	 */
	void insertBoard(BoardVO boardVO) throws Exception;
	
	/**
	 * 게시글 비활성화 Mapper
	 * @param vo
	 * @throws Exception
	 */
	void disableBoard(BoardVO vo) throws Exception;
	
	/**
	 * 게시글 상세보기 Mapper
	 * @param param
	 * @return BoardVO의 리스트 형태
	 * @throws Exception
	 */
	List<BoardVO> selectDetailBaord(Map param) throws Exception;
	
	/**
	 * 활성화 상태 게시글 리스트 Mapper
	 * @return 활성화 상태의 게시글 리스트
	 * @throws Exception
	 */
	List<BoardVO> selectEnableBoards() throws Exception;
	
	/**
	 * 게시글 리스트 페이징에 필요한 게시글 전채수
	 * @param map
	 * @return
	 * @throws Exception
	 */
	int selectBoardsCount(Map map) throws Exception;
	
	/**
	 * 페이징 처리된 게시글 리스트 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<BoardVO> selectPagingBoards(Map map) throws Exception;
	
	void updateBoard(BoardVO boardVO) throws Exception;
}
