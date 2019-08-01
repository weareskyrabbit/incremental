package middle_end;

public interface Optimizer {
    void constant_fold();
    void register_allocate();
    void reduce_deadcode();
}
