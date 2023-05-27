from app.lessons.entites.cell import CellEntity


class CellFixer:
    def fix(self, cells: list[CellEntity]) -> list[CellEntity]:
        fixed_cells: list[CellEntity] = []
        first_cell_map: dict[str, CellEntity] = {}
        for cell in cells:
            try:
                first_cell = first_cell_map[cell.employee_name]
                if first_cell.placement == cell.placement:
                    cell.name = first_cell.name
                    fixed_cells.append(cell)
            except KeyError:
                first_cell_map[cell.employee_name] = cell
                fixed_cells.append(cell)
        return fixed_cells
