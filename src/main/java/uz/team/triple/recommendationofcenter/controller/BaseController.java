package uz.team.triple.recommendationofcenter.controller;

import lombok.RequiredArgsConstructor;
import uz.team.triple.recommendationofcenter.service.BaseService;

@RequiredArgsConstructor
public abstract class BaseController<S extends BaseService>  {
    protected final S service;
}
