package kr.co.ocean.board.controller;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.ocean.board.dto.BoardVO;
import kr.co.ocean.board.service.BoardService;
import kr.co.ocean.kafka.dto.KafkaJsonDTO;
import kr.co.ocean.kafka.service.ProducerService;

@RestController
@RequestMapping("/api/board")
public class BoardController {
	
	private Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	private BoardService boardService;
	private ProducerService producerService;
	
	public BoardController(BoardService boardService, ProducerService producerService) {
		this.boardService = boardService;
		this.producerService = producerService;
	}
	
	@GetMapping("/boardList")
	public List<BoardVO> boardList() throws Exception {
		List<BoardVO> list = boardService.getEnableBoardList();
		System.out.println(list);
		return list;
	}
	
	@PostMapping("/fileupload")
	public Map<String, Object> test123(HttpServletRequest request, 
			@RequestParam(value="file", required=false) MultipartFile[] files) throws Exception {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		String FileNames = "";
		String filepath = "C:/fileUpload/";
		for(MultipartFile mf : files) {
			String oriFileName = mf.getOriginalFilename();
			long fileSize = mf.getSize();
			
			String safeFile = System.currentTimeMillis() + oriFileName;
			
			FileNames = FileNames + "," + safeFile;
			System.out.println(FileNames);
			 
			try {
				File f1 = new File(filepath + safeFile);
				System.out.println(f1.getPath());
				mf.transferTo(f1);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return resultMap;
	}
	
	@PostMapping("/count")
	public int count(@RequestBody Map param) throws Exception {
		return boardService.getCount(param);
	}
	
	@PostMapping("/pagingList")
	public List<BoardVO> pagingList(@RequestBody Map param, HttpServletRequest request) throws Exception {
		logger.info(param.toString());
		logger.info(SecurityContextHolder.getContext().getAuthentication().getName());
		
		String keyword = (String)param.get("keyword");
		if(!keyword.equals("")  && keyword != null) {
			KafkaJsonDTO kafkaJsonDTO = new KafkaJsonDTO();
			kafkaJsonDTO.setDate(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(LocalDateTime.now()));
			kafkaJsonDTO.setData((String)param.get("keyword"));
			
			producerService.sendMessage(SecurityContextHolder.getContext().getAuthentication().getName(),"serchKeyword",kafkaJsonDTO);
		}
		
		List<BoardVO> list = boardService.getBoardList(param);
		
		System.out.println(list);
		return list;
	}
	
	@PostMapping("/createDummy")
	public void createDummy(@RequestBody List<BoardVO> list) throws Exception {		
		for(BoardVO vo : list) {
			boardService.setBoard(vo);
		}
	}
	
	@GetMapping("/detail")
	public BoardVO detail(@RequestParam String no) throws Exception {
		logger.info("[/detail] code: " + no);
		Map param = new HashMap<>();
		param.put("no", no);
		logger.info("[/detail] param: " + param);
		logger.info("[/detail] voList: " + boardService.getBoard(param).toString());
		return boardService.getBoard(param).get(0);
	}
	
	@PostMapping("/addBoard")
	public void addBoard(@RequestBody BoardVO vo) throws Exception {
		System.out.println(vo);
		boardService.setBoard(vo);
	}
	
	@PostMapping("/disable")
	public void disable(@RequestBody BoardVO vo) throws Exception {
		boardService.disableBoard(vo);
	}
	
	@PostMapping("/update")
	public void update(@RequestBody BoardVO vo) throws Exception {
		boardService.updateBoard(vo);
	}
}
