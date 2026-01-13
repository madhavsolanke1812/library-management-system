package com.booktrack.librarymanagementsystem.serviceImp;

import com.booktrack.librarymanagementsystem.dto.AvailabilitySummaryDTO;
import com.booktrack.librarymanagementsystem.dto.BookCreateRequest;
import com.booktrack.librarymanagementsystem.dto.BookDTO;
import com.booktrack.librarymanagementsystem.dto.BookUpdateRequest;
import com.booktrack.librarymanagementsystem.entity.Book;
import com.booktrack.librarymanagementsystem.exception.BadRequestException;
import com.booktrack.librarymanagementsystem.exception.NotFoundException;
import com.booktrack.librarymanagementsystem.mapper.BookMapper;
import com.booktrack.librarymanagementsystem.repository.BookRepository;
import com.booktrack.librarymanagementsystem.repository.BorrowRecordRepository;
import com.booktrack.librarymanagementsystem.service.BookService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImp implements BookService {

    private static final String BOOKS_CACHE = "booksList";
    private final BookRepository bookRepository;
    private final BorrowRecordRepository borrowRecordRepository;

    @Override
    @Transactional
    @CacheEvict(value = BOOKS_CACHE, allEntries = true)
    public BookDTO createOrIncrease(BookCreateRequest request) {
        Optional<Book> existing = bookRepository
                .findByTitleAndAuthorAndSoftDeletedFalse(request.getTitle(),
                        request.getAuthor());

        Book book;
        if (existing.isPresent()) {
            book = existing.get();
            book.setTotalCopies(book.getTotalCopies() + request.getTotalCopies());
            book.setAvailableCopies(book.getAvailableCopies() + request.getTotalCopies());
        } else {
            book = Book.builder()
                    .title(request.getTitle())
                    .author(request.getAuthor())
                    .category(request.getCategory())
                    .totalCopies(request.getTotalCopies())
                    .availableCopies(request.getTotalCopies())
                    .softDeleted(false)
                    .build();
        }
        book = bookRepository.save(book);
        return BookMapper.toDto(book);
    }

    @Override
    @Cacheable(value = BOOKS_CACHE, key = "T(java.util.Objects).hash(#category, #available, #pageable.pageNumber, #pageable.pageSize, #pageable.sort.toString())")
    public Page<BookDTO> listBooks(String category, Boolean available, Pageable pageable) {

        Page<Book> page;
        if (category != null && available != null && available) {

            page = bookRepository.findByCategoryAndSoftDeletedFalse(category, pageable)
                    .map(b -> b.getAvailableCopies() > 0 ? b : null)
                    .map(b -> b);
        } else if (category != null) {
            page = bookRepository.findByCategoryAndSoftDeletedFalse(category, pageable);
        } else {
            page = bookRepository.findAllBySoftDeletedFalse(pageable);
        }

//        // filter
//        if (available != null && available)
//        {
//            page = page.map(b -> b.getAvailableCopies() > 0 ? b : null)
//                    .map(b -> b)
//                    .map(BookMapper::toDto);
//            ;
//        }

        // simpler, map to BookDTO
        return page.map(BookMapper::toDto);
    }

    @Override
    @Transactional
    @CacheEvict(value = BOOKS_CACHE, allEntries = true)
    public BookDTO updateBook(UUID id, BookUpdateRequest req) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book not Found"));
        if (book.isSoftDeleted()) throw new BadRequestException("Book is Deleted");

        // update values of existing book
        book.setTitle(req.getTitle());
        book.setAuthor(req.getAuthor());
        book.setCategory(req.getCategory());

        // adjust copies and make sure that the availableCopies not greater than totalCopies
        book.setTotalCopies(req.getTotalCopies());
        book.setAvailableCopies(Math.min(req.getAvailableCopies(), req.getTotalCopies()));
        book = bookRepository.save(book);
        return BookMapper.toDto(book);
    }

    @Override
    @Transactional
    @CacheEvict(value = BOOKS_CACHE, allEntries = true)
    public void softDelete(UUID id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book not Found"));

        // To check active borrow records for this existing book
        long active = borrowRecordRepository.findActiveRecords().stream()
                .filter(br -> br.getBook().getBookId().equals(id)).count();

        if (active > 0) throw new BadRequestException("Cannot delete book with Active Borrow Records");
        book.setSoftDeleted(true);
        bookRepository.save(book);
    }

    @Override
    public BookDTO getById(UUID id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book not Found"));
        return BookMapper.toDto(book);

    }

    @Override
    public List<BookDTO> findSimilarBooks(UUID bookId) {
        Book base = bookRepository.findById(bookId).orElseThrow(() -> new com.booktrack.librarymanagementsystem.exception.NotFoundException("Book not found"));

        List<Book> categoryMatches = bookRepository.findByCategoryAndSoftDeletedFalse(base.getCategory());

        List<Book> authorMatches = bookRepository.findAll().stream()
                .filter(b -> !b.isSoftDeleted() && !b.getBookId().equals(bookId) && base.getAuthor().equalsIgnoreCase(b.getAuthor()))
                .collect(Collectors.toList());


        LinkedHashSet<Book> combined = new LinkedHashSet<>();
        categoryMatches.stream().filter(b -> !b.getBookId().equals(bookId)).forEach(combined::add);
        authorMatches.forEach(combined::add);

        return combined.stream().map(BookMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<AvailabilitySummaryDTO> availabilitySummary() {
        return availabilitySummary();
    }


}

