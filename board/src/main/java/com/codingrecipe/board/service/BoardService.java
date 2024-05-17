package com.codingrecipe.board.service;

import com.codingrecipe.board.dto.BoardDTO;
import com.codingrecipe.board.dto.BoardFileDTO;
import com.codingrecipe.board.dto.Page;
import com.codingrecipe.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public void save(BoardDTO boardDTO) throws IOException {
        if (boardDTO.getBoardFile().get(0).isEmpty()) {
            // no file
            boardDTO.setFileAttached(0);
            boardRepository.save(boardDTO);
        } else {
            // have file
            boardDTO.setFileAttached(1);
            // 게시글 저장 후 id값 활용위해 리턴받음.
            BoardDTO savedBoard = boardRepository.save(boardDTO);
            // 파일만 따로 가져오기
            for (MultipartFile boardFile : boardDTO.getBoardFile()) {
                // 파일이름
                String originalFilename = boardFile.getOriginalFilename();
                System.out.println("originalFilename: " + originalFilename);
                // 저장용이름만드기
                System.out.println(System.currentTimeMillis()+"-"+originalFilename);
                String storedFileName = System.currentTimeMillis()+"-"+originalFilename;
                System.out.println("storedFileName: " + storedFileName);
                // boardFileDTO 세팅
                BoardFileDTO boardFileDTO = new BoardFileDTO();
                boardFileDTO.setOriginalFileName(originalFilename);
                boardFileDTO.setStoredFileName(storedFileName);
                boardFileDTO.setBoardId(savedBoard.getId());
                //파일 저장용 폴더에 파일 저장처리
                String savePath = "/Users/hyeon/Desktop/abc/"+storedFileName;
                System.out.println("@@@@@BoardService.save"+savePath);
                boardFile.transferTo(new File(savePath));
                // board_file_table 저장처리
                boardRepository.saveFile(boardFileDTO);
            }
        }
    }

    public List<BoardDTO> findAll(Page page) {
        return boardRepository.findAll(page);
    }

    public int count() {
        return boardRepository.count();
    }

    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    public BoardDTO findById(Long id) {
        return boardRepository.findById(id);
    }

    public void update(BoardDTO boardDTO) {
        boardRepository.update(boardDTO);
    }

    public void delete(Long id) {
        boardRepository.delete(id);
    }

    public List<BoardFileDTO> findFile(Long id) {
        return boardRepository.findFile(id);
    }
}
