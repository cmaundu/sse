/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchableRepository<T>
{
    Page<SearchResult<T>> search(String query, boolean useBooleanMode,
                                 Pageable pageable);
}
